package com.ruoyi.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/10 14:41
 */
@Data
@Schema(name = "fileOpusVO", description = "目录作品VO")
public class FolderOpusVO implements Serializable {

    private static final long serialVersionUID = 2822281128087319339L;

    @Schema(description = "当前目录id")
    private Long currentFolderId;

    @Schema(description = "当前目录名称")
    private String currentFolderName;

    @Schema(description = "目录列表")
    private List<FileInfoVO> folderList;

    @Schema(description = "作品列表")
    private List<OpusVO> opusList;

}
