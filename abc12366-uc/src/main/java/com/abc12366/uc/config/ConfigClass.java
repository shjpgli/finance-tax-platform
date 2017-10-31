package com.abc12366.uc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 加载自定义bean文件
 *
 * @author husl on 2017-08-04.
 */
@Configuration
@ImportResource(locations={"classpath:application-bean.xml"})
public class ConfigClass {

}
