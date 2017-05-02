package com.abc12366.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application程序入口
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 2:09 PM
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "com.abc12366.message, com.abc12366.gateway")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
