package com.abc12366.uc.model;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 15:57
 */
public class ExperienceLevel {
    private String id;
    private String name;
    private int value;
    private int topPerDay;
    private Boolean status;
    private Date lastUpdate;
    private Date createTime;

    public ExperienceLevel() {
    }

    public ExperienceLevel(String id, String name, int value, int topPerDay, Boolean status, Date lastUpdate, Date createTime) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.topPerDay = topPerDay;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTopPerDay() {
        return topPerDay;
    }

    public void setTopPerDay(int topPerDay) {
        this.topPerDay = topPerDay;
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
        return "ExperienceLevel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", topPerDay=" + topPerDay +
                ", status=" + status +
                ", lastUpdate=" + lastUpdate +
                ", createTime=" + createTime +
                '}';
    }
}
