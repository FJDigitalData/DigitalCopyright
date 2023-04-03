package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/24 10:04
 */
@Data
@Schema(name = "tagDTO", description = "标签DTO")
public class TagDTO implements Serializable {

    private static final long serialVersionUID = -8343728909537313126L;


    /**
     * 标签
     */
    @NotBlank(message = "标签为空")
    @Length(min = 1, max = 32, message = "标签长度应该在[1,32]")
    @Schema(description = "标签", required = true)
    private String tag;

    /**
     * 风格
     */
    @NotBlank(message = "风格为空")
    @Schema(description = "风格id 取字典sys_opus_style对应的字典值中的dictCode", required = true)
    private String styleId;

}
