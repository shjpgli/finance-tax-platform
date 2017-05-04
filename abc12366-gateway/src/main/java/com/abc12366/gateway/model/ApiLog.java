package com.abc12366.gateway.model;

import com.abc12366.gateway.model.bo.TableBO;

/**
 * 日志信息对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:36 AM
 * @since 1.0.0
 */
public class ApiLog extends TableBO {

    // 主键UUID
    private String id;
    // 访问路径
    private String uri;
    // 用户代理
    private String userAgent;
    // 访问的应用主键
    private String appId;
    // 访问用户ID
    private String userId;
    // IP地址
    private String ip;
    // 访问开始时间
    private long inTime;
    // 访问结束时间
    private long outTime;
    // 结果状态
    private int status;
    // 附言
    private String message;

    public ApiLog() {
    }

    public ApiLog(String yyyyMMdd, String id, String uri, String userAgent, String appId, String userId, String ip,
                  long inTime, long outTime, int status, String message) {
        super(yyyyMMdd);
        this.id = id;
        this.uri = uri;
        this.userAgent = userAgent;
        this.appId = appId;
        this.userId = userId;
        this.ip = ip;
        this.inTime = inTime;
        this.outTime = outTime;
        this.status = status;
        this.message = message;
    }

    private ApiLog(Builder builder) {
        setId(builder.id);
        setUri(builder.uri);
        setUserAgent(builder.userAgent);
        setAppId(builder.appId);
        setUserId(builder.userId);
        setIp(builder.ip);
        setInTime(builder.inTime);
        setOutTime(builder.outTime);
        setStatus(builder.status);
        setMessage(builder.message);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiLog{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", ip='" + ip + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", status=" + status +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }

    public static final class Builder {
        private String id;
        private String uri;
        private String userAgent;
        private String appId;
        private String userId;
        private String ip;
        private long inTime;
        private long outTime;
        private int status;
        private String message;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder uri(String val) {
            uri = val;
            return this;
        }

        public Builder userAgent(String val) {
            userAgent = val;
            return this;
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder ip(String val) {
            ip = val;
            return this;
        }

        public Builder inTime(long val) {
            inTime = val;
            return this;
        }

        public Builder outTime(long val) {
            outTime = val;
            return this;
        }

        public Builder status(int val) {
            status = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public ApiLog build() {
            return new ApiLog(this);
        }
    }
}
