package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 10:25
 */
public class ExperienceLevelInsertAndUpdateBO {
    @NotEmpty
    private String name;
    private int value;
    private int topPerDay;
    private Boolean status;

    public ExperienceLevelInsertAndUpdateBO() {
    }

    public ExperienceLevelInsertAndUpdateBO(String name, int value, int topPerDay, Boolean status) {
        this.name = name;
        this.value = value;
        this.topPerDay = topPerDay;
        this.status = status;
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

    @Override
    public String toString() {
        return "ExperienceLevelInsertAndUpdateBO{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", topPerDay=" + topPerDay +
                ", status=" + status +
                '}';
    }
}
