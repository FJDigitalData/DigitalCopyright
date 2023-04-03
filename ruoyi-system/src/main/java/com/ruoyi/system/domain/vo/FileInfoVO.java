package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "fileInfoVO", description = "文件信息VO")
public class FileInfoVO implements Serializable {

    private static final long serialVersionUID = 877242550693043067L;

    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "id")
    private Long id;

    /**
     * 资源路径
     */
    @Schema(description = "文件资源路径")
    private String url;

    /**
     * 压缩后的资源路径
     */
    @Schema(description = "压缩后的文件资源路径")
    private String zipUrl;

    /**
     * 资源原始名称
     */
    @Schema(description = "资源原始名称")
    private String name;

    /**
     * 资源名称
     */
    @Schema(description = "资源名称")
    private String fileName;

    /**
     * 后缀名
     */
    @Schema(description = "后缀名")
    private String suffix;

    /**
     * 是否图片 Y:是图片 N:不是图片
     */
    @Schema(description = "是否图片 Y:是图片 N:不是图片")
    private String isImg;

    /**
     * 文件展示类型，非后缀名
     */
    @Schema(description = "文件展示类型，非后缀名")
    private String type;

    /**
     * 是否目录 Y:是目录 N:不是目录
     */
    @Schema(description = "是否目录 Y:是目录 N:不是目录")
    private String isDir;

    /**
     * 父级id 顶级目录为0
     */
    @Schema(description = "文件所在目录id 顶级目录为0")
    private String parentId;

    /**
     * 父级名称
     */
    @Schema(description = "父级名称")
    private String parentName;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long creator;

    /**
     * 创建人名称
     */
    @Schema(description = "创建人名称")
    private String creatorName;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updater;

    /**
     * 更新人名称
     */
    @Schema(description = "更新人名称")
    private String updaterName;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
