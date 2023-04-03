package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/1/11 15:57
 */
@Data
@Schema(name = "resetPwdDTO", description = "重置密码DTO")
public class ResetPwdDTO implements Serializable {

    private static final long serialVersionUID = -4349599620340762098L;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "手机号格式错误")
    @Schema(description = "手机号", required = true)
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", required = true)
    private String smsCode;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 5, max = 20, message = "密码长度应在5-20之间")
    @Schema(description = "新密码", required = true)
    private String password;

}
