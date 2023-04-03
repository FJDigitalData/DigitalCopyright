package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/3/6 10:53
 */@Data
@Schema(name = "translateConfigVO", description = "翻译配置VO")
public class TranslateConfigVO implements Serializable {

    private static final long serialVersionUID = -1932888827470276698L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "id")
    private Long id;

    /**
     * 翻译类型 0: 百度翻译 1：有道翻译
     */
    @Schema(description = "翻译类型 0: 百度翻译 1：有道翻译")
    private Integer apiType;

    @Schema(description = "翻译API的应用ID")
    private String appId;

    @Schema(description = "翻译API的密钥")
    private String securityKey;

    /**
     * 公司id
     */
    @Schema(description = "公司id")
    private String companyId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @Schema(description = "删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 是否启用（0-不启用,1-启用）
     */
    @Schema(description = "是否启用（0-不启用,1-启用）")
    private Integer enable;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long creator;

    /**
     * 创建人
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
     * 更新人
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
