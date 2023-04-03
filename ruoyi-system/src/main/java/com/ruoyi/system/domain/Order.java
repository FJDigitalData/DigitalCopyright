package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/6 14:09
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 8185176408278970279L;

    /**
     * 订单状态类型 0: 未支付、1:已支付、2:生产中、3:待发货、4:已发货、5:已完成、6:已取消
     */
    public static final String NOT_PAY = "0";
    public static final String PAY = "1";
    public static final String PRODUCE = "2";
    public static final String WAITTING_DELIVERY = "3";
    public static final String DELIVERY = "4";
    public static final String COMPLETE = "5";
    public static final String CANCEL = "6";

    /**
     * 删除标识（0-正常,1-删除）
     */
    public static final String NOT_DELETE = "0";
    public static final String IS_DELETE = "1";


    /**
     * id(主键)
     */
    private String id;

    /**
     * 第三方平台订单id
     */
    private String orderId;

    /**
     * 订单状态类型 0: 未支付、1:已支付、2:生产中、3:待发货、4:已发货、5:已完成、6:已取消
     */
    private String type;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 物流公司
     */
    private String expressName;

    /**
     * 快递单号
     */
    private String expressId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 订单总额
     */
    private float total;

}
