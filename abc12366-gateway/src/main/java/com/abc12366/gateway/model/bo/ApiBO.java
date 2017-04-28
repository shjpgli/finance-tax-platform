package com.abc12366.gateway.model.bo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 11:28 AM
 * @since 1.0.0
 */
public class ApiBO {

    private String id;

    @NotEmpty
    @Size(min = 4, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 128)
    private String uri;

    // 接口方法
    @NotEmpty
    @Pattern(regexp = "[GET|POST|PUT|DELETE|ALL]", message = "必须为HttpMethod方法")
    private String method;

    // 版本
    @NotEmpty
    @Pattern(regexp = "[1-9]")
    private String version;

    // 接口所属系统
    @NotEmpty
    @Length(max = 64)
    private String appId;

    // 是否需要验证用户身份
    @NotEmpty
    private boolean authentication;

    // 接口状态
    @NotEmpty
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApiBO{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", version='" + version + '\'' +
                ", appId='" + appId + '\'' +
                ", authentication=" + authentication +
                ", status=" + status +
                '}';
    }
}
