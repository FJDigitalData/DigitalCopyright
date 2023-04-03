package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "opusDTO", description = "作品DTO")
public class OpusDTO implements Serializable {

    private static final long serialVersionUID = 3437312293850728023L;

    /**
     * 作品名称
     */
    @NotBlank(message = "作品名称为空")
    @Length(min = 1, max = 128, message = "作品名称长度应在1-128之间")
    @Schema(description = "作品名称", required = true)
    private String name;

    /**
     * 作品风格
     */
    @NotBlank(message = "作品风格为空")
    @Schema(description = "作品风格 取字典sys_opus_style对应的字典值中的dictCode", required = true)
    private String style;

    /**
     * 作品寓意
     */
    @NotBlank(message = "作品寓意为空")
    @Schema(description = "作品寓意", required = true)
    private String inspiration;

    /**
     * 文件id
     */
    @NotNull(message = "作品文件为空")
    @Schema(description = "作品文件id", required = true)
    private Long fileId;

    /**
     * 素材包文件id
     */
    @NotNull(message = "作品素材包为空")
    @Schema(description = "作品素材包文件id", required = true)
    private Long materialFileId;

    @NotNull(message = "请选择作品所在目录")
    @Range(min = 0, message = "作品所在目录不存在")
    @Schema(description = "作品所在目录id 顶级目录为: 0", required = true)
    private Long parentId;

    @NotEmpty(message = "标签不能为空")
    @Schema(description = "标签id集合", required = true)
    private List<String> tags;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    @Schema(description = "价格", required = true)
    private Double price;

}
