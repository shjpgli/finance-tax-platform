package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:47
 */
public class NsrBindQueryBO {
    private String username;
    private String nickname;
    private String nsrsbh;
    private String nsrmc;
    private String swjgmc;
    private boolean status;
    private Date expireDate;
    private String bindType;

    public NsrBindQueryBO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getSwjgmc() {
        return swjgmc;
    }

    public void setSwjgmc(String swjgmc) {
        this.swjgmc = swjgmc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    @Override
    public String toString() {
        return "NsrBindQueryBO{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", nsrmc='" + nsrmc + '\'' +
                ", swjgmc='" + swjgmc + '\'' +
                ", status=" + status +
                ", expireDate=" + expireDate +
                ", bindType='" + bindType + '\'' +
                '}';
    }
}
