package com.abc12366.gateway.model;

import java.util.Date;

/**
 * 应用信息-每个访问应用需要注册才能访问
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 9:39 AM
 * @since 1.0.0
 */
public class App {

    // 主键UUID
    private String id;
    // 应用名称
    private String name;
    // 密码
    private String password;
    // 访问授权码
    private String accessToken;
    // 上次重置Token时间
    private Date lastResetTokenTime;
    // 授权时间起
    private Date startTime;
    // 授权时间止
    private Date endTime;
    // 描述信息
    private String remark;
    // 状态
    private boolean status;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date lastUpdate;

    public App() {
    }

    public App(String id, String name, String password, String accessToken, Date lastResetTokenTime, Date startTime,
               Date endTime, String remark, boolean status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.accessToken = accessToken;
        this.lastResetTokenTime = lastResetTokenTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    private App(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setPassword(builder.password);
        setAccessToken(builder.accessToken);
        setLastResetTokenTime(builder.lastResetTokenTime);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setRemark(builder.remark);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getLastResetTokenTime() {
        return lastResetTokenTime;
    }

    public void setLastResetTokenTime(Date lastResetTokenTime) {
        this.lastResetTokenTime = lastResetTokenTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", lastResetTokenTime='" + lastResetTokenTime + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private String password;
        private String accessToken;
        private Date lastResetTokenTime;
        private Date startTime;
        private Date endTime;
        private String remark;
        private boolean status;
        private Date createTime;
        private Date lastUpdate;

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

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder accessToken(String val) {
            accessToken = val;
            return this;
        }

        public Builder lastResetTokenTime(Date val) {
            lastResetTokenTime = val;
            return this;
        }

        public Builder startTime(Date val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Date val) {
            endTime = val;
            return this;
        }

        public Builder remark(String val) {
            remark = val;
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

        public App build() {
            return new App(this);
        }
    }
}
