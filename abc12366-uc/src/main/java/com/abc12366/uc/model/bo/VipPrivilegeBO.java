package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:51
 */
public class VipPrivilegeBO {
    private String id;
    private String name;//特权名称
    private String css;//class
    private String defaults;//缺省
    private Boolean status;//数据状态
    private Date lastUpdate;
    private Date createTime;
    @Override
    public String toString() {
        return "VipPrivilegeBO [id=" + id + ", name=" + name + ", css=" + css + ", defaults=" + defaults + ", status="
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
    public String getCss() {
        return css;
    }
    public void setCss(String css) {
        this.css = css;
    }
    public String getDefaults() {
        return defaults;
    }
    public void setDefaults(String defaults) {
        this.defaults = defaults;
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
