package com.abc12366.message.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 17:17
 */
public class SmsOps {
    private String id;
    private String mobile;
    private String templateid;
    private String params;
    private String status;
    private String updatetime;
    private Date createTime;
    private Date lastUpdate;
    private String sendid;

    public SmsOps() {
    }

    public SmsOps(String id, String mobile, String templateid, String params, String status, String updatetime, Date createTime, Date lastUpdate, String sendid) {
        this.id = id;
        this.mobile = mobile;
        this.templateid = templateid;
        this.params = params;
        this.status = status;
        this.updatetime = updatetime;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.sendid = sendid;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    @Override
    public String toString() {
        return "SmsOps{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", templateid='" + templateid + '\'' +
                ", params='" + params + '\'' +
                ", status=" + status +
                ", updatetime=" + updatetime +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
