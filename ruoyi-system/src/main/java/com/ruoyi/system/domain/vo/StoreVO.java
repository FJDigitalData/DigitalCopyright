package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/3/2 13:32
 */
@Data
@Schema(name = "storeVO", description = "店铺VO")
public class StoreVO implements Serializable {

    private static final long serialVersionUID = -6540569206468372375L;

    /**
     * ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "id")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "店铺名称")
    private String name;

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
