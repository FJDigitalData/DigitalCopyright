package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.dto.ProductSpecsDTO;
import com.ruoyi.system.domain.dto.ProductVariantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采集商品信息VO
 *
 * @author WangJiang
 * @since 2023/2/7 10:41
 */
@Data
@Schema(name = "erpProductVO", description = "采集商品信息VO")
public class ErpProductVO implements Serializable {

    private static final long serialVersionUID = -7726125095381882877L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String id;

    @Schema(description = "关联作品")
    private String opusId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String name;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private String productPrice;

    /**
     * 商品价格货币代码 字典sys_currency_code的字典键值
     */
    @Schema(description = "商品价格货币代码 字典sys_currency_code的字典键值")
    private String productPriceCode;

    /**
     * 第三方平台采集到的价格
     */
    @Schema(description = "第三方平台采集到的价格")
    private String gatherPrice;

    /**
     * 第三方平台采集到的价格货币代码 字典sys_currency_code的字典键值
     */
    @Schema(description = "第三方平台采集到的价格货币代码 字典sys_currency_code的字典键值")
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
    @Schema(description = "商品描述介绍")
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
     * 售卖形式 0:单品(无规格)  1:变体(有规格)
     */
    @Schema(description = "售卖形式 0:单品(无规格)  1:变体(有规格)")
    private Integer sellType;

    /**
     * 零件编号
     */
    @Schema(description = "零件编号")
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
    @Schema(description = "库存")
    private Integer stock;

    /**
     * 发货时间（天）
     */
    @Schema(description = "发货时间（天）")
    private Integer deliveryTime;

    /**
     * 商品SKU
     */
    @Schema(description = "商品SKU")
    private String productSku;

    /**
     * 品牌
     */
    @Schema(description = "品牌")
    private String brand;

    /**
     * 制造厂商
     */
    @Schema(description = "制造厂商")
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
    @Schema(description = "产品类型")
    private String productType;

    /**
     * 分类类型
     */
    @Schema(description = "分类类型")
    private String category;

    /**
     * 详细类型
     */
    @Schema(description = "详细类型")
    private String detailType;

    /**
     * 原产国
     */
    @Schema(description = "原产国")
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
    @Schema(description = "重量")
    private Double weight;

    /**
     * 待发布：0, 发布中：1, 发布成功：2, 发布失败：3, 部分失败：4, 数据缺失：5
     */
    @Schema(description = "待发布：0, 发布中：1, 发布成功：2, 发布失败：3, 部分失败：4, 数据缺失：5")
    private Integer status;

    /**
     * 已认领：1, 未认领：0
     */
    @Schema(description = "已认领：1, 未认领：0")
    private Integer adoptStatus;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @Schema(description = "删除标识（0-正常,1-删除）")
    private String delFlag;

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

    @Schema(description = "刊登图")
    private List<String> printPhoto;

    @Schema(description = "引用图")
    private List<String> referencePhoto;

    @Schema(description = "规格信息")
    private Map<String, List<String>> specs;

    @Schema(description = "变体信息")
    private List<ProductVariantVO> variantList;

    @Schema(description = "关联作品信息")
    private OpusVO opus;

}
