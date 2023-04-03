package com.ruoyi.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2022/9/29 15:20
 */
@Data
@Schema(name = "fileVO", description = "文件上传结果")
public class FileVO implements Serializable {

    private static final long serialVersionUID = -6395234209808039012L;

    @Schema(description = "文件访问路径")
    private String url;

    @Schema(description = "文件上传后文件名")
    private String fileName;

    @Schema(description = "文件上传后文件名")
    private String newFileName;

    @Schema(description = "文件原文件名")
    private String originalFilename;

}
