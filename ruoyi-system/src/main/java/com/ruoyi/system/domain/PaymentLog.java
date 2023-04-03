package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付日志
 *
 * @author WangJiang
 * @since 2023/3/28 13:36
 */
@Data
public class PaymentLog implements Serializable {

    private static final long serialVersionUID = -9211109035422345041L;

    /**
     * 支付日志id
     */
    private String id;

    /**
     * 状态 1: 发起支付 2: 支付成功 3: 其他
     */
    private Integer status;

    /**
     * 亚马逊卖家支付订单id
     */
    private String orderId;

    /**
     * 第三方支付请求流水
     */
    private String outOrderNo;

    /**
     * 支付方式 0:微信 1:支付宝
     */
    private Integer payType;

    /**
     * 第三方付款流水号
     */
    private String receivableNo;

    /**
     * 支付金额
     */
    private Double totalAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 支付成功时间
     */
    private Date successTime;

}
