package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品变体信息
 *
 * @author wangjiang
 */
@Data
public class ProductVariant implements Serializable {

    private static final long serialVersionUID = -2667563734358671850L;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * id
     */
    private String id;

    /**
     * 图片地址
     */
    private String photo;

    /**
     * 规格信息
     */
    private String specsInfo;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 价格
     */
    private Double price;

    /**
     * 价格货币代码 字典sys_currency_code的字典键值
     */
    private String priceCode;

    /**
     * 采集价格
     */
    private Double gatherPrice;

    /**
     * 采集价格货币代码 字典sys_currency_code的字典键
     */
    private String gatherPriceCode;

    /**
     * 大小型号
     */
    private String sizeMap;

    /**
     * 促销价格
     */
    private Double promotionPrice;

    /**
     * 促销价格货币代码
     */
    private String promotionPriceCode;

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

}
