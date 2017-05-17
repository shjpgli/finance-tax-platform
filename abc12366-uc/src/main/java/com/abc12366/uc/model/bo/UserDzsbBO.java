package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 *
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public class UserDzsbBO {
    private String id;
    private String userId;
    private String djxh;
    private String nsrsbh;
    private String nsrmc;
    private String shxydm;
    private String swjgMc;
    private String swjgDm;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    public UserDzsbBO() {
    }

    public UserDzsbBO(String id, String userId, String djxh, String nsrsbh, String nsrmc, String shxydm, String swjgMc, String swjgDm, boolean status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.userId = userId;
        this.djxh = djxh;
        this.nsrsbh = nsrsbh;
        this.nsrmc = nsrmc;
        this.shxydm = shxydm;
        this.swjgMc = swjgMc;
        this.swjgDm = swjgDm;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
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
        return "UserDzsbBO{" +
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
                '}';
    }
}
