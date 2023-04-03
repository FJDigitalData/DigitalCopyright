package com.ruoyi.system.payment.util;

import com.ruoyi.common.config.RuoYiConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付
 *
 * @author WangJiang
 * @since 2023/3/27 13:32
 */
public interface Payment {

    /**
     * 展示二维码扫描支付
     *
     * @param orderId 订单id
     * @param price   支付金额
     * @return 二维码内容
     */
    String nativePay(String orderId, Double price);

    /**
     * 退款
     * @param transactionId 原订单微信处订单id
     * @param refundId 退款id
     * @param reason 退款原因
     * @param payPrice 支付价格
     * @param refundPrice 退款金额
     */
    void refund(String transactionId, String refundId, String reason, Double payPrice, Double refundPrice);

    /**
     * 回调
     *
     * @param request HttpServletRequest
     */
    void callBack(HttpServletRequest request);

    /**
     * 异步通知
     *
     * @param request HttpServletRequest
     */
    void notify(HttpServletRequest request);

    /**
     * 退款异步通知
     *
     * @param request HttpServletRequest
     */
    default void refundNotify(HttpServletRequest request) {

    }

    /**
     * 支付异步通知地址
     *
     * @param type 支付类型 可选值 WX、ALI
     * @return 异步通知地址
     */
    default String notifyUrl(String type) {
        return RuoYiConfig.getServiceAddress() + "/pay/notify/" + type;
    }

    /**
     * 退款异步通知地址
     *
     * @param type 支付类型 可选值 WX、ALI
     * @return 异步通知地址
     */
    default String refundNotifyUrl(String type) {
        return RuoYiConfig.getServiceAddress() + "/refund/notify/" + type;
    }

}
