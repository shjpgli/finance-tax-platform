package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 11:50
 */
public class ExperienceRuleUpdateBO {
    @Size(max = 32)
    private String name;
    @Size(max = 10)
    private String code;
    private int exp;
    @Size(max = 1000)
    private String description;
    @Size(max = 1)
    private String type;
    private Boolean status;
    @Size(max = 5)
    private String period;
    private Integer degree;

    public ExperienceRuleUpdateBO() {
    }

    public ExperienceRuleUpdateBO(String name, String code, int exp, String description, String type, Boolean status) {
        this.name = name;
        this.code = code;
        this.exp = exp;
        this.description = description;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }
}
