package com.abc12366.bangbang.config;

import com.abc12366.gateway.component.AppInterceptor;
import com.abc12366.gateway.component.LogInterceptor;
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
    public SensitiveWordsInterceptor getSensitiveWordsInterceptor() {
        return new SensitiveWordsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // 敏感词拦截
        registry.addInterceptor(getSensitiveWordsInterceptor())
                .excludePathPatterns("/druid/**");

    }
}
