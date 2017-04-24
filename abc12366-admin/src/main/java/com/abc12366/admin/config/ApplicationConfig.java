package com.abc12366.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取application.properties配置文件内容
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-24 1:36 PM
 * @since 1.0.0
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    // 服务端URL地址
    @Value("${abc12366.gateway.url}")
    public String URL;

    // 服务端用户名
    @Value("${abc12366.gateway.username}")
    public String USERNAME;

    // 服务端密码
    @Value("${abc12366.gateway.password}")
    public String PASSWORD;

    @Value("${spring.kafka.bootstrap-servers}")
    public String BOOTSTRAP_SERVERS;

    @Value("${spring.kafka.consumer.group-id}")
    public String CONSUMER_GROUP_ID;
}
