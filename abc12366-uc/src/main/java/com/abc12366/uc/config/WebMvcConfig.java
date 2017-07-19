package com.abc12366.uc.config;

import com.abc12366.gateway.component.AppInterceptor;
import com.abc12366.gateway.component.LogInterceptor;
import com.abc12366.gateway.component.UcUserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 10:24 AM
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    /*@Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }*/

    @Bean
    public UcUserInterceptor ucUserInterceptor(){
        return new UcUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 前置日志、黑名单、后置日志、接口计数拦截
        registry.addInterceptor(logInterceptor())
                .excludePathPatterns("/druid/**");

        // App验证、授权拦截
        registry.addInterceptor(appInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/auth/**")
                .excludePathPatterns("/test");

        //前台用户访问拦截器迁移到网关后的
        registry.addInterceptor(ucUserInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app*/**")
                .excludePathPatterns("/druid*/**")
                .excludePathPatterns("/auth/**")
                .excludePathPatterns("/login", "/logout/**", "/refresh", "/register", "/test", "/verifylogin", "/user/token/**", "/user/u/**");
    }
}
