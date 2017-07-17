package com.abc12366.gateway.model.bo;

import java.util.Date;

/**
 * AppSetting与Api的关联对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 2:46 PM
 * @since 1.0.0
 */
public class AppSettingApiBO {

    private String id;
    private String appId;
    private String apiId;
    private int timesPerMinute;
    private int timesPerHour;
    private int timesPerDay;
    private String name;
    private String uri;
    private String method;
    private String version;
    private boolean authentication;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    public AppSettingApiBO() {
    }

    public AppSettingApiBO(String id, String appId, String apiId, int timesPerMinute, int timesPerHour, int timesPerDay,
                           String name, String uri, String method, String version, boolean authentication,
                           boolean status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.appId = appId;
        this.apiId = apiId;
        this.timesPerMinute = timesPerMinute;
        this.timesPerHour = timesPerHour;
        this.timesPerDay = timesPerDay;
        this.name = name;
        this.uri = uri;
        this.method = method;
        this.version = version;
        this.authentication = authentication;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    private AppSettingApiBO(Builder builder) {
        setId(builder.id);
        setAppId(builder.appId);
        setApiId(builder.apiId);
        setTimesPerMinute(builder.timesPerMinute);
        setTimesPerHour(builder.timesPerHour);
        setTimesPerDay(builder.timesPerDay);
        setName(builder.name);
        setUri(builder.uri);
        setMethod(builder.method);
        setVersion(builder.version);
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public int getTimesPerMinute() {
        return timesPerMinute;
    }

    public void setTimesPerMinute(int timesPerMinute) {
        this.timesPerMinute = timesPerMinute;
    }

    public int getTimesPerHour() {
        return timesPerHour;
    }

    public void setTimesPerHour(int timesPerHour) {
        this.timesPerHour = timesPerHour;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
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
        return "AppSettingApiBO{" +
                "id='" + id + '\'' +
                ", appId='" + appId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", timesPerMinute=" + timesPerMinute +
                ", timesPerHour=" + timesPerHour +
                ", timesPerDay=" + timesPerDay +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", version='" + version + '\'' +
                ", authentication=" + authentication +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String appId;
        private String apiId;
        private int timesPerMinute;
        private int timesPerHour;
        private int timesPerDay;
        private String name;
        private String uri;
        private String method;
        private String version;
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

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder apiId(String val) {
            apiId = val;
            return this;
        }

        public Builder timesPerMinute(int val) {
            timesPerMinute = val;
            return this;
        }

        public Builder timesPerHour(int val) {
            timesPerHour = val;
            return this;
        }

        public Builder timesPerDay(int val) {
            timesPerDay = val;
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

        public AppSettingApiBO build() {
            return new AppSettingApiBO(this);
        }
    }
}
