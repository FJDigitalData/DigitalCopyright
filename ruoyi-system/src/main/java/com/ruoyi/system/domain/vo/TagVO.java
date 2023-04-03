package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/3/24 10:04
 */
@Data
@Schema(name = "tagVO", description = "风格VO")
public class TagVO implements Serializable {

    private static final long serialVersionUID = -5477051708920802241L;

    @Schema(description = "id")
    private String id;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "风格id")
    private String styleId;

    @Schema(description = "风格名")
    private String styleLabel;

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
