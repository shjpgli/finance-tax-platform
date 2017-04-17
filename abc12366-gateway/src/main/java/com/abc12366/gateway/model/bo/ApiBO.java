package com.abc12366.gateway.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 11:28 AM
 * @since 1.0.0
 */
public class ApiBO {

    private String id;
    private String name;
    private String mark;
    private String method;
    private String role;
    private String version;
    private String appId;
    private String status;

    public ApiBO() {
    }

    private ApiBO(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setMark(builder.mark);
        setMethod(builder.method);
        setRole(builder.role);
        setVersion(builder.version);
        setAppId(builder.appId);
        setStatus(builder.status);
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

    @Override
    public String toString() {
        return "ApiBO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                ", method='" + method + '\'' +
                ", role='" + role + '\'' +
                ", version='" + version + '\'' +
                ", appId='" + appId + '\'' +
                ", status='" + status + '\'' +
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

        public ApiBO build() {
            return new ApiBO(this);
        }
    }
}
