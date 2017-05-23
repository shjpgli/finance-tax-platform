package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-23
 * Time: 11:06
 */
public class ExperienceRuleInsertBO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String code;
    @NotNull
    private Integer exp;
    @Size(max = 1000)
    private String description;
    @Size(max = 1)
    private String type;
    @NotNull
    private Boolean status;

    public ExperienceRuleInsertBO() {
    }

    public ExperienceRuleInsertBO(String name, String code, Integer exp, String description, String type, Boolean status) {
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

    @Override
    public String toString() {
        return "ExperienceRuleInsertBO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", exp=" + exp +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}