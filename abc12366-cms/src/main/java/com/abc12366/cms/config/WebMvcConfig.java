package com.abc12366.cms.config;

import com.abc12366.gateway.component.AppInterceptor;
import com.abc12366.gateway.component.LogInterceptor;
import com.abc12366.gateway.component.TokenInterceptor;
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

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 前置日志、黑名单、后置日志、接口计数拦截
        registry.addInterceptor(logInterceptor())
                .excludePathPatterns("/druid/**");

        // App验证、授权拦截
        registry.addInterceptor(appInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/druid/**")
                        //定时任务查询不需验证
                .excludePathPatterns("/task/list")
                        //修改内容浏览量
                .excludePathPatterns("/content/updateViewsDay")
                        //获取标签
                .excludePathPatterns("/content/selectContentType")
                .excludePathPatterns("/app/login", "/app/register", "/test");

        // UserToken验证、授权拦截
        registry.addInterceptor(tokenInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/app/login", "/app/register", "/test")
                .excludePathPatterns("/admintoken/**", "/user/token/*")
                        //定时任务查询不需验证
                .excludePathPatterns("/task/list")
                        //修改内容浏览量
                .excludePathPatterns("/content/updateViewsDay")
                        //获取标签
                .excludePathPatterns("/content/selectContentType")
                .excludePathPatterns("/login", "/register");

    }
}
