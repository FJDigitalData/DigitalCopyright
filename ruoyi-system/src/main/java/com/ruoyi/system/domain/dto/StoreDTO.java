package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/2 11:34
 */
@Data
@Schema(name = "storeDTO", description = "店铺DTO")
public class StoreDTO implements Serializable {

    private static final long serialVersionUID = 1673756156241297295L;

    @NotBlank(message = "店铺名称不能为空")
    @Length(min = 1, max = 128, message = "店铺名称长度应该在[1, 128]之间")
    @Schema(description = "店铺名称")
    private String name;

}
