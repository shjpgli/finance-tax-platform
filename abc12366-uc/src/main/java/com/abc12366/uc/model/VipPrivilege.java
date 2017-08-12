package com.abc12366.uc.model;

import java.util.Date;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:47
 */
public class VipPrivilege {
    private String id;
    private String name;//特权名称
    private String icon;//图标
    private String description;//描述
    private Boolean status;//数据状态
    private Date lastUpdate;
    private Date createTime;
    @Override
    public String toString() {
        return "VipPrivilege [id=" + id + ", name=" + name + ", icon=" + icon + ", description=" + description + ", status="
                + status + ", lastUpdate=" + lastUpdate + ", createTime=" + createTime + "]";
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
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
}

