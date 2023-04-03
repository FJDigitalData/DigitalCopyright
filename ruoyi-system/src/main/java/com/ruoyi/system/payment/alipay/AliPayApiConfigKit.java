package com.ruoyi.system.payment.alipay;

import cn.hutool.core.date.DateUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.ruoyi.common.config.AliPayConfig;

import java.util.Date;

/**
 * AliPayApiConfigKit
 *
 * @author Chopper
 * @since 2020-12-16 09:31
 */
public class AliPayApiConfigKit {

    /**
     * 支付配置
     */
    static DefaultAlipayClient defaultAlipayClient;

    /**
     * 下次刷新时间
     */
    static Date nextRebuildDate;

    /**
     * 间隔时间
     */
    static Long refreshInterval = 1000 * 60 * 3L;

    /**
     * 获取支付宝支付参数
     *
     * @return
     * @throws AlipayApiException
     */
    public static synchronized DefaultAlipayClient getAliPayApiConfig() throws AlipayApiException {
        Date date = new Date();
        //如果过期，则重新构建
        if (nextRebuildDate == null || date.after(nextRebuildDate)) {
            return rebuild();
        }
        return defaultAlipayClient;
    }

    static DefaultAlipayClient rebuild() throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setAppId(AliPayConfig.getAppId());
        certAlipayRequest.setPrivateKey(AliPayConfig.getPrivateKey());
        certAlipayRequest.setCertPath(AliPayConfig.getCertPath());
        certAlipayRequest.setAlipayPublicCertPath(AliPayConfig.getAlipayPublicCertPath());
        certAlipayRequest.setRootCertPath(AliPayConfig.getRootCertPath());
        defaultAlipayClient = new DefaultAlipayClient(certAlipayRequest);
        nextRebuildDate = DateUtil.date(System.currentTimeMillis() + refreshInterval);
        return defaultAlipayClient;
    }

}
