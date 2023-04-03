package com.ruoyi.system.payment.alipay;

import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ruoyi.common.config.AliPayConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.UlidUtil;
import com.ruoyi.system.mapper.PaymentLogMapper;
import com.ruoyi.system.payment.util.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 支付宝支付
 *
 * @author WangJiang
 * @since 2023/3/27 13:38
 */
@Slf4j
@Component
public class AliPay implements Payment {

    @Resource
    private PaymentLogMapper paymentLogMapper;

    @Override
    public String nativePay(String orderId, Double price) {
        try {
            AlipayTradePrecreateModel payModel = new AlipayTradePrecreateModel();

            //请求订单编号
            String outTradeNo = UlidUtil.getUlid();

            payModel.setBody("艺术品-油画");
            payModel.setSubject("艺术品");
            payModel.setTotalAmount(price + "");

            //回传数据
            payModel.setPassbackParams(URLUtil.encode(orderId));
            payModel.setTimeoutExpress("3m");
            payModel.setOutTradeNo(outTradeNo);
            log.info("支付宝扫码：{}", payModel);
            String resultStr = AliPayRequest.tradePrecreatePayToResponse(payModel, notifyUrl(Constants.ALI_PAY)).getBody();

            log.info("支付宝扫码交互返回：{}", resultStr);
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
        } catch (Exception e) {
            log.error("支付业务异常：", e);
            throw new ServiceException("发起支付宝支付失败");
        }
    }

    @Override
    public void refund(String transactionId, String refundId, String reason, Double payPrice, Double refundPrice) {
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(transactionId);
        model.setRefundAmount(refundPrice + "");
        model.setRefundReason(reason);
        model.setOutRequestNo(refundId);
        //交互退款
        try {
            AlipayTradeRefundResponse alipayTradeRefundResponse = AliPayApi.tradeRefundToResponse(model);
            log.error("支付宝退款，参数：{},支付宝响应：{}", JSONUtil.toJsonStr(model), JSONUtil.toJsonStr(alipayTradeRefundResponse));
            if (alipayTradeRefundResponse.isSuccess()) {
                //发起成功

            } else {

            }
        } catch (Exception e) {
            log.error("发起退款失败， 错误信息：{}", e.getMessage(), e);
            throw new ServiceException("发起支付宝退款失败");
        }
    }

    @Override
    public void callBack(HttpServletRequest request) {
        notify(request);
    }

    @Override
    public void notify(HttpServletRequest request) {
        try {
            //获取支付宝反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            log.info("支付回调响应：{}", JSONUtil.toJsonStr(map));
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(map, AliPayConfig.getAlipayPublicCertPath(), "UTF-8", "RSA2");
            String order = URLUtil.decode(map.get("passback_params"));
            if (verifyResult) {
                String tradeNo = map.get("trade_no");
                Double totalAmount = Double.parseDouble(map.get("total_amount"));
                log.info("支付回调通知：支付成功-参数：{}", map);
            } else {
                log.info("支付回调通知：支付失败-参数：{}", map);
            }
            //ServletUtils.getResponse().sendRedirect("/pages/order/myOrder?status=0");
        } catch (Exception e) {
            log.error("支付回调通知出错，错误信息: {}", e.getMessage(), e);
        }
    }

}
