package com.abc12366.gateway.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * AppSetting与Api的关联对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 2:46 PM
 * @since 1.0.0
 */
public class AppSettingBO {

    private String id;

    @NotEmpty
    private String appId;
    @NotEmpty
    private String apiId;
    private int timesPerMinute;
    private int timesPerHour;
    private int timesPerDay;
    private boolean status;

    public AppSettingBO() {
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
}
