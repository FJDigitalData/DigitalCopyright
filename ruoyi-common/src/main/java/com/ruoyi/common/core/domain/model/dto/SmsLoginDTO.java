package com.ruoyi.common.core.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/1/3 14:34
 */
@Data
@Schema(name = "smsLoginDTO", description = "短信登录DTO")
public class SmsLoginDTO implements Serializable {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", required = true)
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", required = true)
    private String smsCode;

}
