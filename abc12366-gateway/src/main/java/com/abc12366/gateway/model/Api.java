package com.abc12366.gateway.model;

import java.util.Date;

/**
 * API信息表
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 2:55 PM
 * @since 1.0.0
 */
public class Api {

    private String id;
    private String name;
    private String uri;
    // 接口方法
    private String method;
    // 版本
    private String version;
    // 接口所属系统
    private String appId;
    // 是否需要验证用户身份
    private boolean authentication;
    // 接口状态
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    private Api(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setUri(builder.uri);
        setMethod(builder.method);
        setVersion(builder.version);
        setAppId(builder.appId);
        setAuthentication(builder.authentication);
        setStatus(builder.status);
        setCreateTime(builder.createTime);
        setLastUpdate(builder.lastUpdate);
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Api{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", version='" + version + '\'' +
                ", appId='" + appId + '\'' +
                ", authentication=" + authentication +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String method;
        private String version;
        private String appId;
        private boolean authentication;
        private boolean status;
        private Date createTime;
        private Date lastUpdate;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder uri(String val) {
            uri = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder version(String val) {
            version = val;
            return this;
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder authentication(boolean val) {
            authentication = val;
            return this;
        }

        public Builder status(boolean val) {
            status = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder lastUpdate(Date val) {
            lastUpdate = val;
            return this;
        }

        public Api build() {
            return new Api(this);
        }
    }
}
