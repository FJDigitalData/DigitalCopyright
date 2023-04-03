package com.ruoyi.web.controller.other.system;

import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.service.ISmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WangJiang
 * @since 2023/1/3 14:02
 */
@Tag(name = "短信接口")
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Value("${sms.loginTemplateCode}")
    private String loginTemplateCode;

    @Value("${sms.registerTemplateCode}")
    private String registerTemplateCode;

    @Value("${sms.resetPasswordTemplateCode}")
    private String resetPasswordTemplateCode;

    @Resource
    private ISmsService iSmsService;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @RateLimiter(key = CacheConstants.SMS_RATE_LIMIT_KEY, count = 1, limitType = LimitType.IP)
    @Operation(summary = "发送短信验证码", description = "一分钟同一个ip只可以请求1次, 只能在未登录情况下调用", parameters = {
            @Parameter(name = "mobile", description = "手机号", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string", defaultValue = "13067111469")),
            @Parameter(name = "verificationType", description = "验证码类型 1：短信登录(默认) 2:注册验证码 3:修改密码", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "integer", allowableValues = {"1","2","3"}, defaultValue = "1"))
    })
    @GetMapping("/code")
    public AjaxResult getSmsCode(@RequestParam String mobile,
                                 @RequestParam(required = false, defaultValue = "1") Integer verificationType,
                                 HttpServletRequest request) {
        AjaxResult result = AjaxResult.success();
        String code = RandomUtil.randomNumbers(6);
        //准备发送短信参数
        Map<String, String> params = new HashMap<>(2);
        //验证码内容
        params.put("code", code);
        if (verificationType.equals(Constants.SMS_TYPE_LOGIN)) {
            iSmsService.sendSmsCode(mobile, params, loginTemplateCode);
            String ip = IpUtils.getIpAddr(request);
            String redisKey = String.format(Constants.SMS_LOGIN_REDIS_KEY, ip, mobile);
            redisTemplate.boundValueOps(redisKey).set(code, 5L, TimeUnit.MINUTES);
        } else if (verificationType.equals(Constants.SMS_TYPE_REGISTER)) {
            iSmsService.sendSmsCode(mobile, params, registerTemplateCode);
            String ip = IpUtils.getIpAddr(request);
            String redisKey = String.format(Constants.SMS_REGISTER_REDIS_KEY, ip, mobile);
            redisTemplate.boundValueOps(redisKey).set(code, 5L, TimeUnit.MINUTES);
        } else if (verificationType.equals(Constants.SMS_TYPE_RESET_PASSWORD)) {
            iSmsService.sendSmsCode(mobile, params, resetPasswordTemplateCode);
            String ip = IpUtils.getIpAddr(request);
            String redisKey = String.format(Constants.SMS_RESET_PASSWORD_REDIS_KEY, ip, mobile);
            redisTemplate.boundValueOps(redisKey).set(code, 5L, TimeUnit.MINUTES);
        } else {
            result = AjaxResult.error("错误的短信验证码");
        }
        return result;
    }

}
