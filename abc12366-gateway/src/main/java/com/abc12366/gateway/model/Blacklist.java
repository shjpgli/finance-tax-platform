package com.abc12366.gateway.model;

import java.util.Date;

/**
 * 黑名单实体
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 2:02 PM
 * @since 1.0.0
 */
public class Blacklist {

    private String id;
    private String userId;
    // IP地址
    private String ip;
    // 锁定开始时间
    private Date startTime;
    // 锁定结束时间
    private Date endTime;
    private String remark;
    // 状态（1正常、0锁定）
    private boolean status;
    private Date createTime;
    private Date createUser;
    // 辅助属性
    private Date now;

    public Blacklist() {
    }

    public Blacklist(String id, String userId, String ip, Date startTime, Date endTime, String remark, boolean status,
                     Date createTime, Date createUser, Date now) {
        this.id = id;
        this.userId = userId;
        this.ip = ip;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.status = status;
        this.createTime = createTime;
        this.createUser = createUser;
        this.now = now;
    }

    private Blacklist(Builder builder) {
        setId(builder.id);
        setUserId(builder.userId);
        setIp(builder.ip);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setRemark(builder.remark);
        setStatus(builder.status);
        setCreateTime(builder.createTime);
        setCreateUser(builder.createUser);
        setNow(builder.now);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Date createUser) {
        this.createUser = createUser;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", ip='" + ip + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                '}';
    }

    public static final class Builder {
        private String id;
        private String userId;
        private String ip;
        private Date startTime;
        private Date endTime;
        private String remark;
        private boolean status;
        private Date createTime;
        private Date createUser;
        private Date now;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
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

        public Builder createUser(Date val) {
            createUser = val;
            return this;
        }

        public Builder now(Date val) {
            now = val;
            return this;
        }

        public Blacklist build() {
            return new Blacklist(this);
        }
    }
}
