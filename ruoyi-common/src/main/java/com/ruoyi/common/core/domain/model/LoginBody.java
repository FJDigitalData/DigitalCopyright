package com.ruoyi.common.core.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@Schema(name = "loginBody", description = "用户登录对象")
public class LoginBody {

    /**
     * 用户名
     */
    @Schema(description = "用户名", required = true)
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码", required = true)
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码", required = true)
    private String code;

    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识", required = true)
    private String uuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
