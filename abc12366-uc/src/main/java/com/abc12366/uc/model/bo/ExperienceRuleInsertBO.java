package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 11:06
 */
public class ExperienceRuleInsertBO {
    @NotEmpty(message = "规则名称不能为空")
    @Size(max = 32)
    private String name;
    @NotEmpty
    @Size(max = 10)
    private String code;
    @NotNull(message = "经验数值不能为空")
    private Integer exp;
    @Size(max = 1000)
    private String description;
    @Size(max = 1)
    private String type;
    @NotNull
    private Boolean status;
    @NotEmpty(message = "规则周期不能为空")
    private String period;
    @NotNull
    private Integer degree;

    public ExperienceRuleInsertBO() {
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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
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

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}