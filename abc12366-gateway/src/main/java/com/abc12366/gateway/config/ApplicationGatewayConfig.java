package com.abc12366.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取application-gateway.properties配置文件内容
 *
 * @create 2017-06-22 1:36 PM
 * @since 1.0.0
 */
@Configuration
@PropertySource("classpath:application-gateway.properties")
public class ApplicationGatewayConfig {

    /**Admin-Token验证URL*/
    @Value("${admin.token.check.url}")
    public String ADMIN_TOKEN_CHECK_URL;

    /**Admin-Token刷新URL*/
    @Value("${admin.token.refresh.url}")
    public String ADMIN_TOKEN_REFRESH_URL;

}
