package com.abc12366.uc.config;

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
                .excludePathPatterns("/druid/**")
                //第三方交易回调地址
                .excludePathPatterns("/payreturn/**")
                //微信服务回调地址
                .excludePathPatterns("/wechatserver/*");

        // App验证、授权拦截
        registry.addInterceptor(appInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/appsetting/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/blacklist/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/test")
                //第三方交易回调地址
                .excludePathPatterns("/payreturn/**")
                //微信服务回调地址
                .excludePathPatterns("/wechatserver/*");

        //前台用户访问拦截器迁移到网关后的
        registry.addInterceptor(tokenInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/goods/user/**")
                .excludePathPatterns("/appsetting/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/blacklist/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/test")
                        // 用户登录、验证码登录、登出、token刷新、用户注册、测试、token验证自动刷新、验证码
                .excludePathPatterns("/login", "/verifylogin", "/logout/**", "/refresh", "/register", "/user/token/**", "/user/u/**")
                        // 操作员登录、登出、token验证自动刷新
                .excludePathPatterns("/admin/login", "/admin/logout/**", "/admin/token/**")
                        //第三方交易毁回调地址
                .excludePathPatterns("/payreturn/**")
                //微信服务回调地址
                .excludePathPatterns("/wechatserver/*")
                //用户等级接口地址
                .excludePathPatterns("/uvip/level/**");
    }
}
