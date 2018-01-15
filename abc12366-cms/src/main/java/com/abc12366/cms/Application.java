package com.abc12366.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Application程序入口
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 2:09 PM
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "com.abc12366.cms,com.abc12366.gateway")
@PropertySource("classpath:application.properties")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("cms-");
        executor.initialize();
        return executor;
    }
}
