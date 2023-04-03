package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 本地商品采集表
 *
 * @author wangjiang
 */
@Data
public class ErpProduct implements Serializable {

    private static final long serialVersionUID = -2667563734358671850L;

    /**
     * 单品(无规格)
     */
    public static final int PRODUCT_TYPE_SINGLE = 0;

    /**
     * 变体
     */
    public static final int PRODUCT_TYPE_VARIANT = 1;

    /**
     * 未认领
     */
    public static final int ADOPT_STATUS_UN_ADOPTED = 0;

    /**
     * 已认领
     */
    public static final int ADOPT_STATUS_ADOPTED = 1;

    /**
     * 待发布
     */
    public static final int UN_DEPLOYED = 0;

    /**
     * 发布中
     */
    public static final int DEPLOYING = 1;

    /**
     * 已发布
     */
    public static final int DEPLOYED = 2;

    /**
     * 发布失败
     */
    public static final int DEPLOY_FAIL = 3;

    /**
     * 发布部分失败
     */
    public static final int DEPLOY_PART_FAIL = 4;

    /**
     * 数据缺失
     */
    public static final int DEPLOY_MISSING_DATA = 5;

    /**
     * 主键
     */
    private String id;

    /**
     * 父商品id
     */
    private String parentId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private Double productPrice;

    /**
     * 商品价格货币代码 字典sys_currency_code的字典键值
     */
    private String productPriceCode;

    /**
     * 第三方平台采集到的价格
     */
    private Double gatherPrice;

    /**
     * 第三方平台采集到的价格货币代码 字典sys_currency_code的字典键值
     */
    private String gatherPriceCode;

    /**
     * 第三方来源名称
     */
    private String originName;

    /**
     * 第三方采集url
     */
    private String originUrl;

    /**
     * 商品描述介绍
     */
    private String describe;

    /**
     * 要点1
     */
    private String pointOne;

    /**
     * 要点2
     */
    private String pointTwo;

    /**
     * 要点3
     */
    private String pointThree;

    /**
     * 要点4
     */
    private String pointFour;

    /**
     * 要点5
     */
    private String pointFive;

    /**
     * 售卖形式 单品：0 变体：1
     */
    private Integer sellType;

    /**
     * 零件编号
     */
    private String componentId;

    /**
     * 税务编号
     */
    private String taxCode;

    /**
     * 搜索关键字
     */
    private String searchKey;

    /**
     * 超级关键字
     */
    private String superKey;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 发货时间（天）
     */
    private Integer deliveryTime;

    /**
     * 商品SKU
     */
    private String productSku;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 制造厂商
     */
    private String manufacturer;

    /**
     * 区域
     */
    private String areaId;

    /**
     * 店铺
     */
    private String storeId;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 分类类型
     */
    private String category;

    /**
     * 原产国
     */
    private String originCountry;


    /**
     * 详细类型
     */
    private String detailType;

    /**
     * 属性
     */
    private String nature;

    /**
     * 长度
     */
    private Double length;

    /**
     * 宽度
     */
    private Double width;

    /**
     * 高度
     */
    private Double height;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 待发布：0, 发布中：1, 发布成功：2, 发布失败：3, 部分失败：4, 数据缺失：5
     */
    private Integer status;

    /**
     * 已认领：1, 未认领：0
     */
    private Integer adoptStatus;

    /**
     * 删除标识（0-正常,1-删除
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

}
