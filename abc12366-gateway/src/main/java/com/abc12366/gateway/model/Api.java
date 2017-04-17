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
    private String mark;
    private String method;
    private String role;
    private String version;
    private String appId;
    private String status;
    private Date createDate;
    private Date modifyDate;

    private Api(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setMark(builder.mark);
        setMethod(builder.method);
        setRole(builder.role);
        setVersion(builder.version);
        setAppId(builder.appId);
        setStatus(builder.status);
        setCreateDate(builder.createDate);
        setModifyDate(builder.modifyDate);
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "Api{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                ", method='" + method + '\'' +
                ", role='" + role + '\'' +
                ", version='" + version + '\'' +
                ", appId='" + appId + '\'' +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }


    public static final class Builder {
        private String id;
        private String name;
        private String mark;
        private String method;
        private String role;
        private String version;
        private String appId;
        private String status;
        private Date createDate;
        private Date modifyDate;

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

        public Builder mark(String val) {
            mark = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder role(String val) {
            role = val;
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

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder createDate(Date val) {
            createDate = val;
            return this;
        }

        public Builder modifyDate(Date val) {
            modifyDate = val;
            return this;
        }

        public Api build() {
            return new Api(this);
        }
    }
}
