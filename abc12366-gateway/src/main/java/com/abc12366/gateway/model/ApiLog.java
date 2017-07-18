package com.abc12366.gateway.model;

import com.abc12366.gateway.model.bo.TableBO;

import java.io.Serializable;


/**
 * 接口调用日志表
 **/
@SuppressWarnings("serial")
public class ApiLog extends TableBO implements Serializable {

    /**
     * ID
     **/
    private String id;

    /**
     * 访问接口地址
     **/
    private String uri;

    /**
     * 用户代理
     **/
    private String userAgent;

    /**
     * 接入userId
     **/
    private String userId;

    /**
     * 接入AppId
     **/
    private String appId;

    /**
     * 接入IP地址
     **/
    private String ip;

    /**
     * 访问时间
     **/
    private long inTime;

    /**
     * 响应时间
     **/
    private long outTime;

    /**
     * 结果代码
     **/
    private String status;

    /**
     * 附言
     **/
    private String version;

    /**
     * 返回代码
     **/
    private String code;

    /**
     * 返回消息
     **/
    private String message;

    private long startTime;

    private long endTime;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public long getInTime() {
        return this.inTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public long getOutTime() {
        return this.outTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
