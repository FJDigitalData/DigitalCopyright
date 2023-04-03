package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "fileInfoDTO", description = "文件信息DTO")
public class FileInfoDTO implements Serializable {

    private static final long serialVersionUID = -473039170330474898L;

    /**
     * 资源名称
     */
    @NotBlank(message = "目录名称不能为空")
    @Length(min = 1, max = 128, message = "目录名称长度应在1-128之间")
    @Schema(description = "目录名称", required = true)
    private String name;


    /**
     * 父级id 顶级目录为0
     */
    @NotBlank(message = "目录名称不能为空")
    @Length(min = 1, max = 32, message = "目录id长度应在1-32之间")
    @Schema(description = "目录id 根目录id为0", required = true)
    private String parentId;

}
