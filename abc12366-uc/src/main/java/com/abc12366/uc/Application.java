package com.abc12366.uc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Application程序入口
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 2:09 PM
 * @since 1.0.0
 * 2
 */
@SpringBootApplication(scanBasePackages = "com.abc12366.uc, com.abc12366.gateway")
@PropertySource("classpath:application.properties")
@EnableAsync
public class Application extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("uc-");
        executor.initialize();
        return executor;
    }
}
