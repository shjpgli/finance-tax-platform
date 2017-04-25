package com.abc12366.gateway.model;

import java.util.Date;

/**
 * App设置对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 5:43 PM
 * @since 1.0.0
 */
public class AppSetting {

    private String id;
    private String appId;
    private String apiId;
    // 每分钟允许访问次数
    private int timesPerMinute;
    // 每小时允许访问次数
    private int timesPerHour;
    // 每天允许访问次数
    private int timesPerDay;
    // 启停状态: 0停用，1启用
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    private AppSetting(Builder builder) {
        setId(builder.id);
        setAppId(builder.appId);
        setApiId(builder.apiId);
        setTimesPerMinute(builder.timesPerMinute);
        setTimesPerHour(builder.timesPerHour);
        setTimesPerDay(builder.timesPerDay);
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
        return "AppSetting{" +
                "id='" + id + '\'' +
                ", appId='" + appId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", timesPerMinute=" + timesPerMinute +
                ", timesPerHour=" + timesPerHour +
                ", timesPerDay=" + timesPerDay +
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

        public AppSetting build() {
            return new AppSetting(this);
        }
    }
}
