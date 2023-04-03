package com.ruoyi.system.payment.wx;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.config.WxPayConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.UlidUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.PaymentLog;
import com.ruoyi.system.mapper.PaymentLogMapper;
import com.ruoyi.system.payment.util.*;
import com.ruoyi.system.payment.wx.enums.WechatDomain;
import com.ruoyi.system.payment.wx.model.Amount;
import com.ruoyi.system.payment.wx.model.RefundModel;
import com.ruoyi.system.payment.wx.model.UnifiedOrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 微信支付
 *
 * @author WangJiang
 * @since 2023/3/27 13:38
 */
@Slf4j
@Component
public class WxPay implements Payment {

    @Resource
    private PaymentLogMapper paymentLogMapper;

    private static final BigDecimal BASE = BigDecimal.valueOf(100);

    @Override
    public String nativePay(String orderId, Double price) {
        try {
            Integer priceFen = new BigDecimal(price + "").multiply(BASE).intValue();
            //第三方付款订单
            String outOrderNo = UlidUtil.getUlid();
            //过期时间
            String timeExpire = DateUtil.format(DateUtil.offset(DateUtil.date(), DateField.HOUR_OF_DAY, 3), "yyyy-MM-dd'T'HH:mm:ssxxx");
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(WxPayConfig.getAppId())
                    .setMchid(WxPayConfig.getMchId())
                    .setDescription("订单信息")
                    .setOut_trade_no(outOrderNo)
                    .setTime_expire(timeExpire)
                    //回传参数
                    .setAttach(orderId)
                    .setNotify_url(notifyUrl(Constants.WX_PAY))
                    .setAmount(new Amount().setTotal(priceFen));
            String data = JSONUtil.toJsonStr(unifiedOrderModel);
            log.info("统一下单参数 {}", data);
            PaymentHttpResponse response = WechatApi.v3(
                    RequestMethodEnums.POST,
                    WechatDomain.CHINA.toString(),
                    com.ruoyi.system.payment.wx.enums.WechatApi.NATIVE_PAY.toString(),
                    WxPayConfig.getMchId(),
                    WxPayConfig.getSerialNumber(),
                    null,
                    WxPayConfig.getApiClientKey(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );
            log.info("统一下单响应 {}", response);
            //根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, getPlatformCert());
            log.info("verifySignature: {}", verifySignature);
            Assert.isTrue(verifySignature, "发起支付失败");
            return JSONUtil.parseObj(response.getBody()).getStr("code_url");
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            } else {
                log.error("发起微信支付失败，错误信息:{}", e.getMessage(), e);
                throw new ServiceException("发起微信支付失败");
            }
        }
    }

    @Override
    public void refund(String transactionId, String refundId, String reason, Double payPrice, Double refundPrice) {
        try {
            Amount amount = new Amount().setRefund(new BigDecimal(refundPrice + "").multiply(BASE).intValue())
                    .setTotal(new BigDecimal(payPrice + "").multiply(BASE).intValue());

            //退款参数准备
            RefundModel refundModel = new RefundModel()
                    .setTransaction_id(transactionId)
                    .setOut_refund_no(refundId)
                    .setReason(reason)
                    .setAmount(amount)
                    .setNotify_url(refundNotifyUrl(Constants.WX_PAY));

            log.info("微信退款参数 {}", JSONUtil.toJsonStr(refundModel));
            PaymentHttpResponse response = WechatApi.v3(
                    RequestMethodEnums.POST,
                    WechatDomain.CHINA.toString(),
                    com.ruoyi.system.payment.wx.enums.WechatApi.DOMESTIC_REFUNDS.toString(),
                    WxPayConfig.getMchId(),
                    WxPayConfig.getSerialNumber(),
                    null,
                    WxPayConfig.getApiClientKey(),
                    JSONUtil.toJsonStr(refundModel)
            );
            log.info("微信退款响应 {}", response);
            //退款申请成功
            if (response.getStatus() == HttpStatus.SUCCESS) {

            } else {
                //退款申请失败

            }
        } catch (Exception e) {
            log.error("微信退款申请失败", e);
        }
    }

    @Override
    public void callBack(HttpServletRequest request) {
        notify(request);
    }

    @Override
    public void notify(HttpServletRequest request) {
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("微信支付通知密文 {}", result);
            //校验服务器端响应
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    WxPayConfig.getApiKey3(), Objects.requireNonNull(getPlatformCert()));

            log.info("微信支付通知明文 {}", plainText);

            JSONObject jsonObject = JSONUtil.parseObj(plainText);

            String fenPrice = jsonObject.getJSONObject("amount").getStr("total");
            Double totalAmount = new BigDecimal(fenPrice).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue();

            String orderId = jsonObject.getStr("attach"),
                    tradeNo = jsonObject.getStr("transaction_id");
            log.info("微信支付回调：支付成功{}", plainText);
        } catch (Exception e) {
            log.error("支付回调异常， 错误信息:{}", e.getMessage(), e);
        }
    }

    @Override
    public void refundNotify(HttpServletRequest request) {
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("微信退款通知密文 {}", result);
            JSONObject ciphertext = JSONUtil.parseObj(result);
            if (!("REFUND.SUCCESS").equals(ciphertext.getStr("event_type"))) {
                return;
            }
            //校验服务器端响应¬
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    WxPayConfig.getApiKey3(), Objects.requireNonNull(getPlatformCert()));
            log.info("微信退款通知明文 {}", plainText);
            JSONObject jsonObject = JSONUtil.parseObj(plainText);
            String transactionId = jsonObject.getStr("transaction_id");
            String refundId = jsonObject.getStr("refund_id");

        } catch (Exception e) {
            log.error("微信退款失败， 错误信息: {}", e.getMessage(), e);
        }
    }


    /**
     * 获取平台公钥
     *
     * @return 平台公钥
     */
    private X509Certificate getPlatformCert() {
        //获取平台证书列表
        try {
            RedisCache redisCache = null;
            try {
                redisCache = SpringUtils.getBean(RedisCache.class);
            } catch (Exception ignore) {

            }
            if (redisCache != null) {
                String publicCert = redisCache.getCacheObject(Constants.WECHAT_PLAT_FORM_CERT);
                if (StrUtil.isNotBlank(publicCert)) {
                    return PayKit.getCertificate(publicCert);
                }
            }
            PaymentHttpResponse response = WechatApi.v3(
                    RequestMethodEnums.GET,
                    WechatDomain.CHINA.toString(),
                    com.ruoyi.system.payment.wx.enums.WechatApi.GET_CERTIFICATES.toString(),
                    WxPayConfig.getMchId(),
                    WxPayConfig.getSerialNumber(),
                    null,
                    WxPayConfig.getApiClientKey(),
                    ""
            );
            String body = response.getBody();
            log.info("获取微信平台证书body: {}", body);
            if (response.getStatus() == 200) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                //默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                String associatedData = encryptCertificate.getStr("associated_data");
                String cipherText = encryptCertificate.getStr("ciphertext");
                String nonce = encryptCertificate.getStr("nonce");
                String publicCert = getPlatformCertStr(associatedData, nonce, cipherText);
                if (redisCache != null) {
                    int second = (int) ((PayKit.getCertificate(publicCert).getNotAfter().getTime() - System.currentTimeMillis()) / 1000);
                    redisCache.setCacheObject(Constants.WECHAT_PLAT_FORM_CERT, publicCert, second, TimeUnit.SECONDS);
                }
                return PayKit.getCertificate(publicCert);
            } else {
                log.error("证书获取失败：{}" + body);
                throw new IllegalArgumentException("获取微信支付证书失败");
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            } else {
                log.error("证书获取失败， 错误信: {}", e.getMessage());
                throw new IllegalArgumentException("获取微信支付证书失败");
            }
        }
    }

    /**
     * 获取平台证书缓存的字符串
     * 下列各个密钥参数
     *
     * @param associatedData 密钥参数
     * @param nonce          密钥参数
     * @param cipherText     密钥参数
     * @return platform key
     * @throws GeneralSecurityException 密钥获取异常
     */
    private String getPlatformCertStr(String associatedData, String nonce, String cipherText) throws GeneralSecurityException {
        AesUtil aesUtil = new AesUtil(WxPayConfig.getApiKey3().getBytes(StandardCharsets.UTF_8));
        //平台证书密文解密
        //encrypt_certificate 中的  associated_data nonce  ciphertext
        return aesUtil.decryptToString(
                associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8),
                cipherText
        );
    }

}
