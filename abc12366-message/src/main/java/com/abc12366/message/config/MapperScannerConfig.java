package com.abc12366.message.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis映射文件扫描配置
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 9:41 AM
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter(DataBaseConfig.class)
public class MapperScannerConfig {

    @Bean
    public MapperScannerConfigurer db1MapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        // 设置自动扫描包, 该包下的Mapper(Dao)将会被mybatis自动注册, 不用写实现类
        configurer.setBasePackage("com.abc12366.message.mapper");
        configurer.setSqlSessionFactoryBeanName("db1SqlSessionFactory");
        return configurer;
    }
}
