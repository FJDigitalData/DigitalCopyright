package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "opusVO", description = "作品VO")
public class OpusVO implements Serializable {

    private static final long serialVersionUID = -8648855929089565591L;

    @Schema(description = "id")
    private String id;

    @Schema(description = "作品名称")
    private String name;

    @Schema(description = "作品风格")
    private String style;

    @Schema(description = "作品风格名称")
    private String styleName;

    @Schema(description = "作品寓意")
    private String inspiration;

    @Schema(description = "是否上架 0:未上架 1:已上架")
    private String isOpen;

    @Schema(description = "是否冻结 0:未冻结 1:已冻结")
    private String isFreeze;

    @Schema(description = "作品文件id")
    private Long fileId;

    @Schema(description = "作品压缩后的文件地址")
    private String fileZipUrl;

    @Schema(description = "素材包文件id")
    private Long materialFileId;

    @Schema(description = "素材包文件地址")
    private String materialFileUrl;

    @Schema(description = "作品所在目录id 顶级目录为0")
    private Long parentId;

    @Schema(description = "版权拥有者id")
    private Long copyrightOwner;

    @Schema(description = "版权拥有者名称")
    private String copyrightOwnerName;

    @Schema(description = "版权申请状态 0：未申请 1：待审核 2：驳回 3：审核通过")
    private String applyStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "版权申请时间")
    private Date applyTime;

    @Schema(description = "驳回理由")
    private String applyRemark;

    @Schema(description = "区块版权标识")
    private String blockIdentity;

    @Schema(description = "浏览量")
    private Long hot;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private Double price;

    @Schema(description = "是否可以申请授权")
    private Boolean couldGrantAuthorization;

    @Schema(description = "是否已经申请过授权")
    private Boolean isAuthorized;

    @Schema(description = "删除标识（0-正常,1-删除）")
    private String delFlag;

    @Schema(description = "创建人")
    private Long creator;

    @Schema(description = "创建人名称")
    private String creatorName;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "更新人")
    private Long updater;

    @Schema(description = "更新人名称")
    private String updaterName;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(description = "当前文件为目录时，该目录下的子文件和子目录")
    private List<OpusVO> children;

    @Schema(description = "标签")
    private List<TagVO> tags;

    @Schema(description = "授权用户列表")
    private List<EmpowerVO> empowerList;

    @Schema(description = "作品文件信息")
    private FileInfoVO fileInfo;

    @Schema(description = "素材包文件信息")
    private FileInfoVO materialFileInfo;

}
