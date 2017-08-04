package com.abc12366.uc.model.bo;

import java.util.Date;


/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 16:18
 */
public class ExperienceLevelBO {
    private String id;
    private String name;
    private Integer minValue;
    private Integer maxValue;
    private Integer topPerDay;
    private Boolean status;
    private Date lastUpdate;
    private Date createTime;
    private String medal;
    private String medalIcon;

    public ExperienceLevelBO() {
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

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getTopPerDay() {
        return topPerDay;
    }

    public void setTopPerDay(Integer topPerDay) {
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

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public String getMedalIcon() {
        return medalIcon;
    }

    public void setMedalIcon(String medalIcon) {
        this.medalIcon = medalIcon;
    }
}
