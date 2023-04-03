package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/10 10:17
 */
@Data
@Schema(name = "folderOpusTreeItemVO", description = "目录作品树节点VO")
public class FolderOpusTreeItemVO implements Serializable {

    private static final long serialVersionUID = 6443528695198751908L;

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 资源名称
     */
    @Schema(description = "名称")
    private String name;

    @Schema(description = "资源类型 1：文件夹 2：作品")
    private Integer type;

    @Schema(description = "所在目录id")
    private Long parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "子文件")
    private List<FolderOpusTreeItemVO> children;

}
