package com.abc12366.message.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 17:00
 */
public class SmsOpsLog {
    private String id;
    private String templateid;
    private String mobiles;
    private String params;
    private String code;
    private String msg;
    private String obj;
    private Date createTime;

    public SmsOpsLog() {
    }

    public SmsOpsLog(String id, String templateid, String mobiles, String params, String code, String msg, String obj, Date createTime) {
        this.id = id;
        this.templateid = templateid;
        this.mobiles = mobiles;
        this.params = params;
        this.code = code;
        this.msg = msg;
        this.obj = obj;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    @Override
    public String toString() {
        return "SmsOpsLog{" +
                "id='" + id + '\'' +
                ", templateid='" + templateid + '\'' +
                ", mobiles='" + mobiles + '\'' +
                ", params='" + params + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
