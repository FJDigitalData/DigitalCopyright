package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/3/2 15:50
 */
@Data
@Schema(name = "storeAuthorityVO", description = "店铺授权")
public class StoreAuthorityVO implements Serializable {

    private static final long serialVersionUID = 3464928170849654240L;

    @Schema(description = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Schema(description = "店铺所属公司id")
    private String companyId;

    @Schema(description = "所授权的店铺在亚马逊系统中的店铺id")
    private String shopId;

    @Schema(description = "所授权的店铺在油画系统中的店铺id")
    private String selfShopId;

    @Schema(description = "")
    private String region;

    @Schema(description = "卖家授权码")
    private String mwsAuthToken;

    @Schema(description = "")
    private String disable;

    @Schema(description = "")
    private String name;

    @Schema(description = "")
    private String pictureId;

    @Schema(description = "授权状态 -1: 废弃 0:未授权 1:授权中 2:授权完成")
    private Integer status;

    @Schema(description = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusUpdate;

    @Schema(description = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productDate;

    @Schema(description = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refreshInvTime;

    @Schema(description = "")
    private String refreshToken;

    @Schema(description = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refreshTokenTime;

    @Schema(description = "")
    private String awsRegion;

    @Schema(description = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    @Schema(description = "")
    private String oldId;

    @Schema(description = "")
    private String accessKeyId;

    @Schema(description = "")
    private String secretKey;

    @Schema(description = "")
    private String roleArn;

    @Schema(description = "")
    private String clientId;

    @Schema(description = "")
    private String clientSecret;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @Schema(description = "删除标识（0-正常,1-删除）")
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
