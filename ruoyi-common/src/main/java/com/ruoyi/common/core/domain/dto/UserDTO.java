package com.ruoyi.common.core.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/1/6 10:55
 */
@Data
@Schema(name = "userDTO", description = "用户注册信息DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -6768104628335680330L;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "手机号格式错误")
    @Schema(description = "手机号", required = true)
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", required = true)
    private String smsCode;

    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 5, max = 20, message = "用户密码长度应在5-20之间")
    @Schema(description = "密码", required = true)
    private String password;

    @NotBlank(message = "用户类型不能为空")
    @Schema(description = "用户类型 01: 创作者 02: 电商售卖者", required = true, allowableValues = {"01", "02"})
    private String userType;

}
