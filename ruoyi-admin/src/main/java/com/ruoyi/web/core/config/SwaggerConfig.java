package com.ruoyi.web.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2的接口配置
 *
 * @author ruoyi
 */
@EnableKnife4j
@Configuration
public class SwaggerConfig {

    /**
     * 是否开启swagger
     */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /**
     * 设置请求的host
     */
    @Value("${swagger.host}")
    private String host;

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String authorization;

    /**
     * 系统基础配置
     */
    private final RuoYiConfig ruoyiConfig;

    @Autowired
    public SwaggerConfig(RuoYiConfig ruoyiConfig) {
        this.ruoyiConfig = ruoyiConfig;
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> operation.addSecurityItem(securityRequirement());
    }

    @Bean
    public OpenAPI customOpenApi() {
        Contact contact = new Contact();
        contact.setName(ruoyiConfig.getName());
        return new OpenAPI()
                .info(new Info()
                        .title("油画版权系统接口文档")
                        .version(ruoyiConfig.getVersion())
                        .contact(contact).description("油画版权系统")
                        .termsOfService(host))
                .addSecurityItem(securityRequirement())
                .components(new Components().addSecuritySchemes(authorization, securityScheme()));
    }

    @Bean
    public GroupedOpenApi systemApi(OperationCustomizer operationCustomizer) {
        // {"/**"} 包下所有请求
        String[] paths = {"/sms/**", "/system/user/**", "/system/role/**", "/login", "/sms-login", "/getInfo", "/getRouters", "/system/dict/data", "/common/minio/*", "/common/*", "/system/dict/data/**"};
        String[] packagedToMatch = {"com.ruoyi.web.controller.other"};
        return GroupedOpenApi.builder().group("1-系统接口").pathsToMatch(paths).addOperationCustomizer(operationCustomizer).packagesToScan(packagedToMatch).build();
    }

    @Bean
    public GroupedOpenApi businessApi(OperationCustomizer operationCustomizer) {
        String[] paths = {"/business/**"}; String[] packagedToMatch = {"com.ruoyi.web.controller.business"};
        return GroupedOpenApi.builder().group("2-业务接口").pathsToMatch(paths).addOperationCustomizer(operationCustomizer).packagesToScan(packagedToMatch).build();
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(authorization);
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().name(authorization).type(SecurityScheme.Type.HTTP).in(SecurityScheme.In.HEADER).scheme("bearer").bearerFormat("JWT");
    }

}
