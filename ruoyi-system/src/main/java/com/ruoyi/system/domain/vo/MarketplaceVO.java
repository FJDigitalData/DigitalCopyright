package com.ruoyi.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/2 14:22
 */
@Data
public class MarketplaceVO implements Serializable {

    private static final long serialVersionUID = -8617416443936186668L;

    @Schema(description = "")
    private String id;

    /**
     * 站点简码
     */
    @Schema(description = "站点简码")
    private String market;

    /**
     * 站点名称
     */
    @Schema(description = "站点名称")
    private String name;

    /**
     * 所属区域简码
     */
    @Schema(description = "所属区域简码")
    private String region;

    /**
     * 所属区域名称
     */
    @Schema(description = "所属区域名称")
    private String regionName;

    /**
     * 所属区域站点
     */
    @Schema(description = "所属区域站点")
    private String endPoint;

    /**
     *
     */
    @Schema(description = "")
    private String pointName;

    /**
     *
     */
    @Schema(description = "")
    private String accessKey;

    /**
     *
     */
    @Schema(description = "")
    private String secretKey;

    /**
     *
     */
    @Schema(description = "")
    private String dimUnits;

    /**
     *
     */
    @Schema(description = "")
    private String weightUnits;

    /**
     *
     */
    @Schema(description = "")
    private String currency;

    /**
     *
     */
    @Schema(description = "")
    private Integer index;

    /**
     *
     */
    @Schema(description = "")
    private String advEndPoint;

    /**
     *
     */
    @Schema(description = "")
    private String awsAccessKey;

    /**
     *
     */
    @Schema(description = "")
    private String awsSecretKey;

    /**
     *
     */
    @Schema(description = "")
    private String associateTag;

    /**
     *
     */
    @Schema(description = "")
    private String developerUrl;

    /**
     *
     */
    @Schema(description = "")
    private String devAccountNum;

    /**
     *
     */
    @Schema(description = "")
    private String bytecode;

    /**
     *
     */
    @Schema(description = "")
    private String spApiEndpoint;

    /**
     *
     */
    @Schema(description = "")
    private String awsRegion;
}
