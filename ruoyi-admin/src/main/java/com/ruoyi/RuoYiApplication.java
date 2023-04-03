package com.ruoyi;

import com.ruoyi.common.utils.ip.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@EnableAspectJAutoProxy(exposeProxy=true)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RuoYiApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuoYiApplication.class);

    public static void main(String[] args) {
        Environment environment = SpringApplication.run(RuoYiApplication.class, args).getEnvironment();
        String envPort = environment.getProperty("server.port");
        String envContext = environment.getProperty("server.contextPath");
        String port = envPort == null ? "8001" : envPort;
        String context = envContext == null ? "" : envContext;
        String path = port + "" + context;
        String externalApi = IpUtils.getHostIp();
        LOGGER.info("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ  \nAccess URLs:\n----------------------------------------------------------\n\t"
                        + "Local-API: \t\thttp://127.0.0.1:{}\n\t"
                        + "External-API: \thttp://{}:{}\n\t"
                        + "swagger-url: \thttp://127.0.0.1:{}/doc.html\n\t----------------------------------------------------------",
                path, externalApi, path, port);
    }

}
