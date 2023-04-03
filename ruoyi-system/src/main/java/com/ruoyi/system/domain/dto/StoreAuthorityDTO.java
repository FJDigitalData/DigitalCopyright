package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/2 15:49
 */
@Data
@Schema(name = "storeAuthorityDTO", description = "授权DTO")
public class StoreAuthorityDTO implements Serializable {

    private static final long serialVersionUID = -8432445029378110459L;

    @NotBlank(message = "state字段不能为空")
    @Schema(description = "取亚马逊授权回调链接中的state字段", required = true, example = "3@us-east-1")
    private String state;

    @NotBlank(message = "selling_partner_id字段不能为空")
    @Schema(description = "取亚马逊授权回调链接中的selling_partner_id字段", required = true, example = "A1ACM5X09OQYJQ")
    private String sellingPartnerId;

    @Schema(description = "取亚马逊授权回调链接中的mws_auth_token字段 该参数在授权成功后重定向的链接中可能没有")
    private String mwsAuthToken;

    @NotBlank(message = "spapi_oauth_code字段不能为空")
    @Schema(description = "取亚马逊授权回调链接中的spapi_oauth_code字段", required = true, example = "ANZgTuSsbuFWzjMhfemO")
    private String spApiOauthCode;

}
