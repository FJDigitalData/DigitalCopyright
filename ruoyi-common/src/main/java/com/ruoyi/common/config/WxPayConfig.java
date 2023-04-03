package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/27 13:59
 */
@Configuration
@ConfigurationProperties(prefix = "pay.wx")
public class WxPayConfig implements Serializable {

    private static final long serialVersionUID = -1067571674973934189L;

    private static String appId;

    private static String mchId;

    private static String apiKey3;

    private static String apiClientKey;

    private static String serialNumber;

    private static String apiClientCertPem;

    private static String apiClientCertP12;

    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        WxPayConfig.appId = appId;
    }

    public static String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        WxPayConfig.mchId = mchId;
    }

    public static String getApiKey3() {
        return apiKey3;
    }

    public void setApiKey3(String apiKey3) {
        WxPayConfig.apiKey3 = apiKey3;
    }

    public static String getApiClientKey() {
        return apiClientKey;
    }

    public void setApiClientKey(String apiClientKey) {
        WxPayConfig.apiClientKey = apiClientKey;
    }

    public static String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        WxPayConfig.serialNumber = serialNumber;
    }

    public static String getApiClientCertPem() {
        return apiClientCertPem;
    }

    public void setApiClientCertPem(String apiClientCertPem) {
        WxPayConfig.apiClientCertPem = apiClientCertPem;
    }

    public static String getApiClientCertP12() {
        return apiClientCertP12;
    }

    public void setApiClientCertP12(String apiClientCertP12) {
        WxPayConfig.apiClientCertP12 = apiClientCertP12;
    }

}
