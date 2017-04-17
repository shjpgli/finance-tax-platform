package com.abc12366.gateway.model;

/**
 * 日志信息对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:36 AM
 * @since 1.0.0
 */
public class Log {

    // 主键UUID
    private String id;
    // 访问路径
    private String uri;
    //    用户代理
    private String userAgent;
    //    IP地址
    private String ip;
    //    访问开始时间
    private long startTime;
    //    访问结束时间
    private long endTime;
    // 结果状态
    private int status;
    // 访问的应用主键
    private String appId;

    private Log(Builder builder) {
        setId(builder.id);
        setUri(builder.uri);
        setUserAgent(builder.userAgent);
        setIp(builder.ip);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setStatus(builder.status);
        setAppId(builder.appId);
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", ip='" + ip + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", appId='" + appId + '\'' +
                '}';
    }


    public static final class Builder {
        private String id;
        private String uri;
        private String userAgent;
        private String ip;
        private long startTime;
        private long endTime;
        private int status;
        private String appId;

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

        public Builder ip(String val) {
            ip = val;
            return this;
        }

        public Builder startTime(long val) {
            startTime = val;
            return this;
        }

        public Builder endTime(long val) {
            endTime = val;
            return this;
        }

        public Builder status(int val) {
            status = val;
            return this;
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Log build() {
            return new Log(this);
        }
    }
}
