package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 采集商品信息DTO
 *
 * @author WangJiang
 * @since 2023/2/7 10:41
 */
@Data
@Schema(name = "erpProductDTO", description = "采集商品信息DTO")
public class ErpProductDTO implements Serializable {

    private static final long serialVersionUID = -7726125095381882877L;

    /**
     * 关联作品id
     */
    @NotBlank(message = "关联作品不能为空")
    @Schema(description = "关联作品", required = true)
    private String opusId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @Length(min = 1, max = 64, message = "商品名长度应该在[1,64]")
    @Schema(description = "商品名称", required = true, example = "哇哈哈")
    private String name;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
    @Schema(description = "商品价格", required = true, example = "6.6")
    private Double productPrice;

    /**
     * 商品价格货币代码 字典sys_currency_code的字典键值
     */
    @NotBlank(message = "商品价格货币代码不能为空")
    @Schema(description = "商品价格货币代码 字典sys_currency_code的字典键值", required = true, example = "CNY")
    private String productPriceCode;

    /**
     * 第三方平台采集到的价格
     */
    @NotNull(message = "第三方平台价格不能为空")
    @Schema(description = "第三方平台价格", required = true, example = "6.6")
    private Double gatherPrice;

    /**
     * 第三方平台采集到的价格货币代码 字典sys_currency_code的字典键值
     */
    @NotBlank(message = "第三方平台采集价格货币代码不能为空")
    @Schema(description = "第三方平台采集到的价格货币代码 字典sys_currency_code的字典键值", required = true, example = "CNY")
    private String gatherPriceCode;

    /**
     * 第三方来源名称
     */
    @Schema(description = "第三方来源名称")
    private String originName;

    /**
     * 第三方采集url
     */
    @Schema(description = "第三方采集url")
    private String originUrl;

    /**
     * 商品描述介绍
     */
    @NotBlank(message = "商品描述介绍不能为空")
    @Schema(description = "商品描述介绍", required = true, example = "我是商品介绍")
    private String describe;

    /**
     * 要点1
     */
    @Schema(description = "要点1")
    private String pointOne;

    /**
     * 要点2
     */
    @Schema(description = "要点2")
    private String pointTwo;

    /**
     * 要点3
     */
    @Schema(description = "要点3")
    private String pointThree;

    /**
     * 要点4
     */
    @Schema(description = "要点4")
    private String pointFour;

    /**
     * 要点5
     */
    @Schema(description = "要点5")
    private String pointFive;

    /**
     * 零件编号
     */
    @Schema(description = "零件编号 草稿箱商品编辑时必填")
    private String componentId;

    /**
     * 税务编号
     */
    @Schema(description = "税务编号")
    private String taxCode;

    /**
     * 搜索关键字
     */
    @Schema(description = "搜索关键字")
    private String searchKey;

    /**
     * 超级关键字
     */
    @Schema(description = "超级关键字")
    private String superKey;

    /**
     * 库存
     */
    @Schema(description = "库存 草稿箱商品编辑时必填")
    private Integer stock;

    /**
     * 发货时间（天）
     */
    @Schema(description = "发货时间（天）")
    private Integer deliveryTime;

    /**
     * 商品SKU
     */
    @Schema(description = "商品SKU 草稿箱商品编辑时必填")
    private String productSku;

    /**
     * 品牌
     */
    @Schema(description = "品牌 草稿箱商品编辑时必填")
    private String brand;

    /**
     * 制造厂商
     */
    @Schema(description = "制造厂商 草稿箱商品编辑时必填")
    private String manufacturer;

    /**
     * 区域
     */
    @Schema(description = "区域")
    private String areaId;

    /**
     * 店铺
     */
    @Schema(description = "店铺")
    private String storeId;

    /**
     * 产品类型
     */
    @Schema(description = "产品类型 草稿箱商品编辑时必填")
    private String productType;

    /**
     * 分类类型
     */
    @Schema(description = "分类类型")
    private String category;

    /**
     * 详细类型
     */
    @Schema(description = "详细类型 草稿箱商品编辑时必填")
    private String detailType;

    /**
     * 原产国
     */
    @Schema(description = "原产国 草稿箱商品编辑时必填")
    private String originCountry;

    /**
     * 属性
     */
    @Schema(description = "属性")
    private String nature;

    /**
     * 长度
     */
    @Schema(description = "长度")
    private Double length;

    /**
     * 宽度
     */
    @Schema(description = "宽度")
    private Double width;

    /**
     * 高度
     */
    @Schema(description = "高度")
    private Double height;

    /**
     * 重量
     */
    @Schema(description = "重量 草稿箱商品编辑时必填")
    private Double weight;

    @NotEmpty(message = "刊登图不能为空")
    @Schema(description = "刊登图", required = true, example = "[\"http://120.78.177.193:9000/ming-byte/0936007D105B22092BFF3B69F383D3E3C8B7D81673335055471.png\"]")
    private List<String> printPhoto;

    @NotEmpty(message = "引用图不能为空")
    @Schema(description = "引用图", required = true, example = "[\"http://120.78.177.193:9000/ming-byte/0936007D105B22092BFF3B69F383D3E3C8B7D81673335055471.png\"]")
    private List<String> referencePhoto;

    @NotNull(message = "商品类型不能为空")
    @Schema(description = "售卖形式 0:单品(无规格)  1:变体(有规格)", required = true, allowableValues = "")
    private Integer sellType;

    @Valid
    @Schema(description = "规格信息", required = true)
    private List<ProductSpecsDTO> specs;

    @Valid
    @Schema(description = "变体信息", required = true)
    private List<ProductVariantDTO> variantList;

}
