package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/27 13:59
 */
@Configuration
@ConfigurationProperties(prefix = "pay.ali")
public class AliPayConfig implements Serializable {

    private static final long serialVersionUID = -3442032181505872983L;

    private static String appId;

    private static String privateKey;

    private static String certPath;

    private static String alipayPublicCertPath;

    private static String rootCertPath;

    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        AliPayConfig.appId = appId;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        AliPayConfig.privateKey = privateKey;
    }

    public static String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        AliPayConfig.certPath = certPath;
    }

    public static String getAlipayPublicCertPath() {
        return alipayPublicCertPath;
    }

    public void setAlipayPublicCertPath(String alipayPublicCertPath) {
        AliPayConfig.alipayPublicCertPath = alipayPublicCertPath;
    }

    public static String getRootCertPath() {
        return rootCertPath;
    }

    public void setRootCertPath(String rootCertPath) {
        AliPayConfig.rootCertPath = rootCertPath;
    }

}
