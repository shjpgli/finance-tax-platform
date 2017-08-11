package com.abc12366.message.model;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-10
 * Time: 18:19
 */
public class MessageSendLog {
    private String id;
    private String sendchanel;
    private String biztype;
    private String sendinfo;
    private Date sendtime;
    private String sendstatus;
    private String failcode;
    private String failcause;
    private Date logtime;

    public MessageSendLog() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendchanel() {
        return sendchanel;
    }

    public void setSendchanel(String sendchanel) {
        this.sendchanel = sendchanel;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public String getSendinfo() {
        return sendinfo;
    }

    public void setSendinfo(String sendinfo) {
        this.sendinfo = sendinfo;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendstatus() {
        return sendstatus;
    }

    public void setSendstatus(String sendstatus) {
        this.sendstatus = sendstatus;
    }

    public String getFailcode() {
        return failcode;
    }

    public void setFailcode(String failcode) {
        this.failcode = failcode;
    }

    public String getFailcause() {
        return failcause;
    }

    public void setFailcause(String failcause) {
        this.failcause = failcause;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }
}
