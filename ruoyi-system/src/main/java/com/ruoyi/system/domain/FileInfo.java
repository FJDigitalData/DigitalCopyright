package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/6 14:09
 */
@Data
public class FileInfo implements Serializable {

    private static final long serialVersionUID = -6016747230831628698L;

    /**
     * id
     */
    private Long id;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 压缩后的资源路径
     */
    private String zipUrl;

    /**
     * 资源原始名称
     */
    private String name;

    /**
     * 资源名称
     */
    private String fileName;

    /**
     * 后缀名
     */
    private String suffix;

    /**
     * 是否图片 Y:是图片 N:不是图片
     */
    private String isImg;

    /**
     * 非后缀名
     */
    private String type;

    /**
     * 是否目录 Y:是目录 N:不是目录
     */
    private String isDir;

    /**
     * 父级id 顶级目录为0
     */
    private String parentId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateTime;

}
