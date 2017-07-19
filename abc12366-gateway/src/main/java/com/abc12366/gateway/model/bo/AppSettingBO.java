package com.abc12366.gateway.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


/**
 * 接入应用设置表
 **/
@SuppressWarnings("serial")
public class AppSettingBO implements Serializable {

    /**
     * ID
     **/
    private String id;


    /**
     * APPID
     **/
    @NotEmpty
    private String appId;

    /**
     * 接口ID
     **/
    @NotEmpty
    private String apiId;

    /**
     * 每分钟允许访问次数
     **/
    private Integer timesPerMinute;

    /**
     * 每小时允许访问次数
     **/
    private Integer timesPerHour;

    /**
     * 每天允许访问次数
     **/
    private Integer timesPerDay;

    /**
     * 启停状态: 0停用，1启用
     **/
    private Boolean status;

    /**
     * 创建时间
     **/
    private java.util.Date createTime;

    /**
     * 修改时间
     **/
    private java.util.Date lastUpdate;

    /**接口名称**/
    private String name;

    /**接口地址**/
    private String uri;

    /**接口方法**/
    private String method;

    /**版本**/
    private String version;

    /**是否需要验证用户身份: 0不需要，1需要**/
    private Boolean authentication;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiId() {
        return this.apiId;
    }

    public void setTimesPerMinute(Integer timesPerMinute) {
        this.timesPerMinute = timesPerMinute;
    }

    public Integer getTimesPerMinute() {
        return this.timesPerMinute;
    }

    public void setTimesPerHour(Integer timesPerHour) {
        this.timesPerHour = timesPerHour;
    }

    public Integer getTimesPerHour() {
        return this.timesPerHour;
    }

    public void setTimesPerDay(Integer timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public Integer getTimesPerDay() {
        return this.timesPerDay;
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

    public Boolean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }
}
