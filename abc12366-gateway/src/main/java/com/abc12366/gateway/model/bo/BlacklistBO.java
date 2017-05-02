package com.abc12366.gateway.model.bo;

import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-28 11:49 AM
 * @since 1.0.0
 */
public class BlacklistBO {

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

    @Override
    public String toString() {
        return "BlacklistBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", ip='" + ip + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
