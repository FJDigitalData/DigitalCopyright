package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品规格信息DTO
 *
 * @author WangJiang
 * @since 2023/2/7 11:04
 */
@Data
@Schema(name = "productSpecsDTO", description = "商品规格信息DTO")
public class ProductSpecsDTO implements Serializable {

    private static final long serialVersionUID = -509503310915217022L;

    /**
     * 规格名
     */
    @NotBlank(message = "规格名不能为空")
    @Length(min = 1, max = 32, message = "规格名长度应该在[1,32]")
    @Schema(description = "规格名", required = true, example = "颜色")
    private String specsName;

    /**
     * 规格值
     */
    @NotBlank(message = "规格值不能为空")
    @Length(min = 1, max = 32, message = "规格值长度应该在[1,32]")
    @Schema(description = "规格值", required = true, example = "红色")
    private String specsValue;

}
