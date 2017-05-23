package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:24
 */
public class VipLevelUpdateBO {
    @NotEmpty
    private String id;
    private String level;
    private double factor;
    private boolean status;
    private Date lastUpdate;
    private Date createTime;

    public VipLevelUpdateBO() {
    }

    public VipLevelUpdateBO(String id, String level, double factor, boolean status, Date lastUpdate, Date createTime) {
        this.id = id;
        this.level = level;
        this.factor = factor;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VipLevelUpdateBO{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", factor=" + factor +
                ", status=" + status +
                ", lastUpdate=" + lastUpdate +
                ", createTime=" + createTime +
                '}';
    }
}
