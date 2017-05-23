package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-19 10:18 PM
 * @since 2.0.0
 */
public class VipLevelBO {
    private String id;
    @NotEmpty
    private String level;
    private double factor;
    @NotNull
    private Boolean status;
    private Date lastUpdate;
    private Date createTime;

    public VipLevelBO() {
    }

    public VipLevelBO(String id, String level, double factor, Boolean status, Date lastUpdate, Date createTime) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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
        return "VipLevelBO{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", factor=" + factor +
                ", status=" + status +
                ", lastUpdate=" + lastUpdate +
                ", createTime=" + createTime +
                '}';
    }
}
