package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/2/7 11:47
 */
@Data
@Schema(name = "productVariantVO", description = "商品变体信息VO")
public class ProductVariantVO implements Serializable {

    private static final long serialVersionUID = -5806864061233077704L;

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsId;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String photo;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specsInfo;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Integer stock;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private Double price;

    /**
     * 价格货币代码 字典sys_currency_code的字典键值
     */
    @Schema(description = "价格货币代码")
    private String priceCode;

    /**
     * 采集价格
     */
    @Schema(description = "采集价格")
    private Double gatherPrice;

    /**
     * 采集价格货币代码 字典sys_currency_code的字典键
     */
    @Schema(description = "采集价格货币代码")
    private String gatherPriceCode;

    /**
     * 大小型号
     */
    @Schema(description = "大小型号")
    private String sizeMap;

    /**
     * 促销价格
     */
    @Schema(description = "促销价格")
    private Double promotionPrice;

    /**
     * 促销价格货币代码
     */
    @Schema(description = "促销价格货币代码")
    private String promotionPriceCode;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long creator;

    /**
     * 创建人昵称
     */
    @Schema(description = "创建人昵称")
    private String creatorNickName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updater;

    /**
     * 更新人昵称
     */
    @Schema(description = "更新人昵称")
    private String updaterNickName;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
