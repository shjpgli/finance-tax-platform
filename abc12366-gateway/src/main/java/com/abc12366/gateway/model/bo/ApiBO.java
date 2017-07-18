package com.abc12366.gateway.model.bo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 服务接口表
 **/
@SuppressWarnings("serial")
public class ApiBO implements Serializable {

    /**
     * ID
     **/
    private String id;

    /**
     * 接口名称
     **/
    @NotEmpty
    @Size(min = 4, max = 50)
    private String name;

    /**
     * 接口地址
     **/
    @NotEmpty
    @Size(min = 1, max = 128)
    private String uri;

    /**
     * 接口方法
     **/
    @NotEmpty
    @Pattern(regexp = "GET|POST|PUT|DELETE|ALL", message = "必须为HttpMethod方法")
    private String method;

    /**
     * 版本
     **/
    @NotEmpty
    @Pattern(regexp = "[1-9]")
    private String version;

    /**
     * 接口所属系统
     **/
    @NotEmpty
    @Length(max = 64)
    private String appId;

    /**
     * 是否需要验证用户身份: 0不需要，1需要
     **/
    @NotNull
    private Boolean authentication;

    /**
     * 接口状态：0停用，1启用
     **/
    @NotNull
    private Boolean status;

    /**
     * 创建时间
     **/
    private java.util.Date createTime;

    /**
     * 最后修改时间
     **/
    private java.util.Date lastUpdate;

    private String appName;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }

    public Boolean getAuthentication() {
        return this.authentication;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public java.util.Date getLastUpdate() {
        return this.lastUpdate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
