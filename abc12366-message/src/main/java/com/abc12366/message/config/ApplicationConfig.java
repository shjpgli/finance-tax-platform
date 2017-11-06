package com.abc12366.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置文件
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 10:56 AM
 * @since 1.0.0
 */
@Configuration
public class ApplicationConfig {

    // kafka服务器地址
//    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrap_servers;

    // kafka消费者group-id
//    @Value("${spring.kafka.consumer.group-id}")
    public String consumer_group_id;

    @Value("${message.netease.appKey}")
    private String appKey;
    @Value("${message.netease.appSecret}")
    private String appSecret;
    @Value("${message.netease.contentType}")
    private String contentType;
    @Value("${message.netease.charset}")
    private String charset;
    
    

	//电子税局地址
    @Value("${dzsj.soa.url}")
    public String dzsjUrl;

    //电子税局APPID
    @Value("${dzsj.soa.appId}")
    public String appId;

    //电子税局密码
    @Value("${dzsj.soa.secret}")
    public String secret;
    
    public String getDzsjUrl() {
		return dzsjUrl;
	}

	public void setDzsjUrl(String dzsjUrl) {
		this.dzsjUrl = dzsjUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

    public String getBootstrap_servers() {
        return bootstrap_servers;
    }

    public void setBootstrap_servers(String bootstrap_servers) {
        this.bootstrap_servers = bootstrap_servers;
    }

    public String getConsumer_group_id() {
        return consumer_group_id;
    }

    public void setConsumer_group_id(String consumer_group_id) {
        this.consumer_group_id = consumer_group_id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
