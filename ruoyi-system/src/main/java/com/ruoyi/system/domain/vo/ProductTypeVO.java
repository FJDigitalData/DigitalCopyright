package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/21 16:17
 */
@Data
@Schema(name = "productTypeVO", description = "亚马逊商品分类信息VO")
public class ProductTypeVO implements Serializable {

    private static final long serialVersionUID = 6786098977706507795L;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "子分类")
    private List<ProductTypeVO> child;

}
