package com.abc12366.uc.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by lgy on 2017-05-04.
 */
public class UCToken {
    private String id;
    @NotEmpty(message = "appId不能为空")
    private String appId;
    @NotEmpty(message = "userId不能为空")
    private String userId;
    private String token;
    private Date lastTokenResetDate;

    public UCToken() {
    }

    @Override
    public String toString() {
        return "UCToken{" +
                "id='" + id + '\'' +
                ", appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", lastTokenResetDate=" + lastTokenResetDate +
                '}';
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastTokenResetDate() {
        return lastTokenResetDate;
    }

    public void setLastTokenResetDate(Date lastTokenResetDate) {
        this.lastTokenResetDate = lastTokenResetDate;
    }

    public UCToken(String id, String appId, String userId, String token, Date lastTokenResetDate) {

        this.id = id;
        this.appId = appId;
        this.userId = userId;
        this.token = token;
        this.lastTokenResetDate = lastTokenResetDate;
    }
}
