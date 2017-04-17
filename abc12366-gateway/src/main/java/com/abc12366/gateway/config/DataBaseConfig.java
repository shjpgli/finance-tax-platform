package com.abc12366.gateway.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * 数据库配置文件
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-21 11:15 AM
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
public class DataBaseConfig implements EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfig.class);

    private Environment environment;
    private static RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        if (propertyResolver == null) {
            propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.db1.");
        }
    }

    @Bean(name = "db1DataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource db1DataSource() throws SQLException {
        // druid数据源
        if (StringUtils.isEmpty(propertyResolver.getProperty("url"))) {
            System.out.println("Your database connection pool configuration is incorrect!" +
                    " Please check your Spring profile, current profiles are:" +
                    Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException(
                    "Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertyResolver.getProperty("driverClassName"));
        druidDataSource.setUrl(propertyResolver.getProperty("url"));
        druidDataSource.setUsername(propertyResolver.getProperty("username"));
        druidDataSource.setPassword(propertyResolver.getProperty("password"));
        druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
        druidDataSource.setFilters(propertyResolver.getProperty("filters"));
        druidDataSource.setUseGlobalDataSourceStat(Boolean.parseBoolean(propertyResolver.getProperty("useGlobalDataSourceStat")));
        return druidDataSource;
    }

    @Bean("db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory db1SqlSessionFactory() throws SQLException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(db1DataSource());
        // 自动重命名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.abc12366.gateway.model");
        // 设置 typeHandler
//        sqlSessionFactoryBean.setTypeHandlersPackage("com.example.project.typeHandler");
        // 添加拦截器插件 (如果有的话, 分页插件, 分表插件等)
//        sqlSessionFactory.setPlugins(new Interceptor[]{pageHelper});
        // 设置 mapper 文件
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sqlSessionFactoryBean.setMapperLocations(resolver
                    .getResources("classpath:com/abc12366/gateway/mapper/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            LOGGER.error("初始化DB1SqlSessionFactory失败", e);
            throw new RuntimeException(e);
        }
    }

    @Bean("db1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate() throws SQLException {
        return new SqlSessionTemplate(db1SqlSessionFactory());
    }

    @Bean("db1TxManager")
    @Primary
    public PlatformTransactionManager db1TxManager() throws SQLException {
        return new DataSourceTransactionManager(db1DataSource());
    }
}
