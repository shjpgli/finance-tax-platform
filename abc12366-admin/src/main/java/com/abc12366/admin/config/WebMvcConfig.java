package com.abc12366.admin.config;

import com.abc12366.gateway.component.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    public AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    @Bean
    public AdminUserInterceptor getAdminUserInterceptor() {
        return new AdminUserInterceptor();
    }

    @Bean
    public IpInterceptor ipInterceptor() {
        return new IpInterceptor();
    }

    @Bean
    public UcUserInterceptor getUcUserInterceptor() {
        return new UcUserInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //IP地址拦截
//        registry.addInterceptor(ipInterceptor()).excludePathPatterns("/druid/**");

        // 前置日志、黑名单、后置日志、接口计数拦截
        registry.addInterceptor(logInterceptor())
                .excludePathPatterns("/druid/**");

        // App验证、授权拦截
        registry.addInterceptor(appInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/admintoken/**")
                .excludePathPatterns("/app/login", "/app/register", "/test");

        // UserToken验证、授权拦截
        /*registry.addInterceptor(userInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/druid*//**")
                .excludePathPatterns("/app/login", "/app/register", "/test")
                .excludePathPatterns("/login", "/register");
*/
        // 用户Token拦截
        registry.addInterceptor(getAdminUserInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/app/login", "/app/register", "/test")
                .excludePathPatterns("/admintoken/**")
                .excludePathPatterns("/login", "/register");
    }
}
