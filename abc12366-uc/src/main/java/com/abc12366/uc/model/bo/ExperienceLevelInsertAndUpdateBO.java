package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 10:25
 */
public class ExperienceLevelInsertAndUpdateBO {
    @NotEmpty
    @Size(max = 10)
    private String name;
    @NotNull
    private Integer minValue;
    @NotNull
    private Integer maxValue;
    private Integer topPerDay;
    private Boolean status;
    @Size(max = 50)
    private String medal;
    @Size(max = 50)
    private String medalIcon;

    public ExperienceLevelInsertAndUpdateBO() {
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
