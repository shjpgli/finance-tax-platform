package com.abc12366.message.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * HttpSession配置
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-14 4:05 PM
 * @since 1.0.0
 */
@Configuration
public class HttpSessionConfig {

    @Autowired
    private RedisClusterConfig config;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory(new RedisClusterConfiguration(config.getNodes()));
    }
}
