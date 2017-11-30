package com.abc12366.uc.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-26
 * Time: 11:06
 */
public class UserDzsbListBO {
    private String id;
    private String userId;
    private String djxh;
    private String nsrsbh;
    private String nsrmc;
    private String shxydm;
    private String swjgMc;
    private String swjgDm;
    private Boolean status;
    private Date createTime;
    private Date lastUpdate;
    private String userType;
    private Date expandExpireTime;

    /**
     * 软件到期日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expireTime;

    public UserDzsbListBO() {
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

    public String getDjxh() {
        return djxh;
    }

    public void setDjxh(String djxh) {
        this.djxh = djxh;
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

    public String getShxydm() {
        return shxydm;
    }

    public void setShxydm(String shxydm) {
        this.shxydm = shxydm;
    }

    public String getSwjgMc() {
        return swjgMc;
    }

    public void setSwjgMc(String swjgMc) {
        this.swjgMc = swjgMc;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getExpandExpireTime() {
        return expandExpireTime;
    }

    public void setExpandExpireTime(Date expandExpireTime) {
        this.expandExpireTime = expandExpireTime;
    }

    @Override
    public String toString() {
        return "UserDzsbListBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", djxh='" + djxh + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", nsrmc='" + nsrmc + '\'' +
                ", shxydm='" + shxydm + '\'' +
                ", swjgMc='" + swjgMc + '\'' +
                ", swjgDm='" + swjgDm + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", userType='" + userType + '\'' +
                ", expandExpireTime=" + expandExpireTime +
                '}';
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "UserDzsbListBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", djxh='" + djxh + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", nsrmc='" + nsrmc + '\'' +
                ", shxydm='" + shxydm + '\'' +
                ", swjgMc='" + swjgMc + '\'' +
                ", swjgDm='" + swjgDm + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                ", expireTime=" + expireTime +
                '}';
    }
}
