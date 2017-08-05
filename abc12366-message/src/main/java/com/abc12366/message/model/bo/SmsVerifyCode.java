package com.abc12366.message.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 15:32
 */
public class SmsVerifyCode {
    private String id;
    private String mobile;
    private String deviceId;
    private String templateid;
    private String code;
    private String msg;
    private String obj;
    private Date createTime;
    private Date lastUpdate;

    public SmsVerifyCode() {
    }

    public SmsVerifyCode(String id, String mobile, String deviceId, String templateid, String code, String msg,
                         String obj, Date createTime, Date lastUpdate) {
        this.id = id;
        this.mobile = mobile;
        this.deviceId = deviceId;
        this.templateid = templateid;
        this.code = code;
        this.msg = msg;
        this.obj = obj;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
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

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    @Override
    public String toString() {
        return "SmsVerifyCode{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", templateid='" + templateid + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
