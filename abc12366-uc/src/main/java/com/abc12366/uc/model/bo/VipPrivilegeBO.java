package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:51
 */
public class VipPrivilegeBO {
    private String id;
    private String name;
    private String level;
    private String remark;
    private Boolean status;
    private Date lastUpdate;
    private Date createTime;

    public VipPrivilegeBO() {
    }

    public VipPrivilegeBO(String id, String name, String level, String remark, Boolean status, Date lastUpdate, Date createTime) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.remark = remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "VipPrivilegeBO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", lastUpdate=" + lastUpdate +
                ", createTime=" + createTime +
                '}';
    }
}
