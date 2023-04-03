package com.ruoyi.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.properties.DruidProperties;
import com.ruoyi.framework.datasource.DynamicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 *
 * @author ruoyi
 */
@Configuration
public class DruidConfig {

    private final Logger log = LoggerFactory.getLogger(DruidConfig.class);

    private FlywayProperties flywayProperties;

    public DruidConfig() {
        try {
            this.flywayProperties = SpringUtils.getBean(FlywayProperties.class);
        } catch (Exception ignore) {

        }
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        DruidDataSourceWrapper masterDruidDataSource = ((DruidDataSourceWrapper) masterDataSource);
        init(masterDruidDataSource.getDriverClassName(), masterDruidDataSource.getUrl(), masterDruidDataSource.getUsername(), masterDruidDataSource.getPassword());
        Map<Object, Object> targetDataSources = new HashMap<>(8);
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 设置数据源
     *
     * @param targetDataSources 备选数据源集合
     * @param sourceName        数据源名称
     * @param beanName          bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception ignore) {
        }
    }

    /**
     * 去除监控页面底部的广告
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
    public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        final String filePath = "support/http/resources/js/common.js";
        // 创建filter进行过滤
        Filter filter = new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
            }
        };
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }

    public void init(String driverClassName, String databaseUrl, String databaseUsername, String databasePassword) {
        Connection conn = null;
        Statement stmt = null;
        try {
            if (flywayProperties != null) {
                log.info("开始创建数据库(存在则不创建)");
                //STEP 1: Register JDBC driver
                Class.forName(driverClassName);
                String databaseName = databaseUrl.substring(databaseUrl.lastIndexOf("=") + 1);
                //STEP 2: Open a connection
                conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
                stmt = conn.createStatement();
                //拼接建库语句 String sql = String.format("CREATE DATABASE IF NOT EXISTS `%s` CHARACTER SET `utf8mb4`;", databaseName);
                String sql = String.format("CREATE SCHEMA IF NOT EXISTS %s;", databaseName);
                // executeUpdate:执行给定的SQL语句，该语句可以是INSERT，UPDATE或DELETE语句或不返回任何内容的SQL语句，例如SQL DDL语句。
                log.info("执行建库语句: {}", sql);
                //STEP 3: Execute sql
                stmt.executeUpdate(sql);
                log.info("创建数据库完成");
                //STEP 4: Run flyway
                migrate(databaseUrl, databaseUsername, databasePassword);
            }
        } catch (Exception e) {
            log.error("初始化数据库失败，错误原因: {}", e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception ignored) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {

            }
        }
    }

    public void migrate(String databaseUrl, String databaseUsername, String databasePassword) {
        Flyway flyway = initConfig(databaseUrl, databaseUsername, databasePassword).load();
        try {
            log.info("开始处理sql脚本");
            flyway.migrate();
            log.info("处理sql脚本结束");
        } catch (Exception e) {
            log.error("Flyway第一次加载出错，错误信息：{}", e.getMessage(), e);
            try {
                log.info("开始修复sql脚本");
                flyway.repair();
                log.info("修复sql脚本结束");
                log.info("开始第二次处理sql脚本");
                flyway.migrate();
                log.info("第二次处理sql脚本结束");
            } catch (Exception se) {
                log.error("Flyway第二次加载出错，错误信息：{}", e.getMessage(), e);
                throw se;
            }
        }
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    private FluentConfiguration initConfig(String databaseUrl, String databaseUsername, String databasePassword) {
        FluentConfiguration configuration = Flyway.configure()
                .dataSource(databaseUrl, databaseUsername, databasePassword)
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .encoding(flywayProperties.getEncoding())
                .connectRetries(flywayProperties.getConnectRetries())
                .schemas(flywayProperties.getSchemas().toArray(new String[0]))
                .createSchemas(flywayProperties.isCreateSchemas())
                .table(flywayProperties.getTable())
                .baselineDescription(flywayProperties.getBaselineDescription())
                .baselineVersion(flywayProperties.getBaselineVersion())
                .installedBy(flywayProperties.getInstalledBy())
                .placeholders(flywayProperties.getPlaceholders())
                .placeholderPrefix(flywayProperties.getPlaceholderPrefix())
                .placeholderSuffix(flywayProperties.getPlaceholderSuffix())
                .placeholderReplacement(flywayProperties.isPlaceholderReplacement())
                .sqlMigrationPrefix(flywayProperties.getSqlMigrationPrefix())
                .sqlMigrationSuffixes(flywayProperties.getSqlMigrationSuffixes().toArray(new String[0]))
                .sqlMigrationSeparator(flywayProperties.getSqlMigrationSeparator())
                .repeatableSqlMigrationPrefix(flywayProperties.getRepeatableSqlMigrationPrefix())
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .cleanDisabled(flywayProperties.isCleanDisabled())
                .cleanOnValidationError(flywayProperties.isCleanOnValidationError())
                .group(flywayProperties.isGroup())
                .mixed(flywayProperties.isMixed())
                .outOfOrder(flywayProperties.isOutOfOrder())
                .skipDefaultCallbacks(flywayProperties.isSkipDefaultCallbacks())
                .skipDefaultResolvers(flywayProperties.isSkipDefaultResolvers())
                .validateMigrationNaming(flywayProperties.isValidateMigrationNaming())
                .validateOnMigrate(flywayProperties.isValidateOnMigrate());
        if (flywayProperties.getLockRetryCount() != null) {
            configuration.lockRetryCount(flywayProperties.getLockRetryCount());
        }
        if (flywayProperties.getDefaultSchema() != null) {
            configuration.defaultSchema(flywayProperties.getDefaultSchema());
        }
        if (flywayProperties.getTablespace() != null) {
            configuration.tablespace(flywayProperties.getTablespace());
        }
        if (flywayProperties.getTarget() != null) {
            configuration.target(flywayProperties.getTarget());
        }
        if (flywayProperties.getBatch() != null) {
            configuration.batch(flywayProperties.getBatch());
        }
        if (flywayProperties.getDryRunOutput() != null) {
            configuration.dryRunOutput(flywayProperties.getDryRunOutput());
        }
        if (flywayProperties.getErrorOverrides() != null) {
            configuration.errorOverrides(flywayProperties.getErrorOverrides());
        }
        if (flywayProperties.getLicenseKey() != null) {
            configuration.licenseKey(flywayProperties.getLicenseKey());
        }
        if (flywayProperties.getOracleSqlplus() != null) {
            configuration.oracleSqlplus(flywayProperties.getOracleSqlplus());
        }
        if (flywayProperties.getOracleSqlplusWarn() != null) {
            configuration.oracleSqlplusWarn(flywayProperties.getOracleSqlplusWarn());
        }
        if (flywayProperties.getStream() != null) {
            configuration.stream(flywayProperties.getStream());
        }
        if (flywayProperties.getUndoSqlMigrationPrefix() != null) {
            configuration.undoSqlMigrationPrefix(flywayProperties.getUndoSqlMigrationPrefix());
        }
        if (flywayProperties.getCherryPick() != null) {
            configuration.cherryPick(flywayProperties.getCherryPick());
        }
        if (!CollectionUtils.isEmpty(flywayProperties.getJdbcProperties())) {
            configuration.jdbcProperties(flywayProperties.getJdbcProperties());
        }
        if (flywayProperties.getOracleKerberosCacheFile() != null) {
            configuration.oracleKerberosCacheFile(flywayProperties.getOracleKerberosCacheFile());
        }
        if (flywayProperties.getOutputQueryResults() != null) {
            configuration.outputQueryResults(flywayProperties.getOutputQueryResults());
        }
        if (flywayProperties.getSkipExecutingMigrations() != null) {
            configuration.skipExecutingMigrations(flywayProperties.getSkipExecutingMigrations());
        }
        return configuration;
    }

}
