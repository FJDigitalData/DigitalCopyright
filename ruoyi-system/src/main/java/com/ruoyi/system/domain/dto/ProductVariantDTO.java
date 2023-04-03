package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品变体信息DTO
 *
 * @author wangjiang
 */
@Data
@Schema(name = "productVariantDTO", description = "商品变体信息DTO")
public class ProductVariantDTO implements Serializable {

    private static final long serialVersionUID = -8298176977534020869L;

    /**
     * 图片地址
     */
    @NotBlank(message = "图片地址不能为空")
    @Schema(description = "图片地址", required = true, example = "http://120.78.177.193:9000/ming-byte/0936007D105B22092BFF3B69F383D3E3C8B7D81673335055471.png")
    private String photo;

    /**
     * 规格信息
     */
    @NotBlank(message = "规格信息不能为空")
    @Schema(description = "规格信息", required = true, example = "{\"颜色\":\"红色\"}")
    private String specsInfo;

    /**
     * 库存
     */
    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    @Schema(description = "库存", required = true, example = "99")
    private Integer stock;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    @Schema(description = "价格", required = true, example = "6.6")
    private Double price;

    /**
     * 价格货币代码 字典sys_currency_code的字典键值
     */
    @NotBlank(message = "价格货币代码不能为空")
    @Schema(description = "价格货币代码  字典sys_currency_code的字典键值", required = true, example = "CNY")
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

}