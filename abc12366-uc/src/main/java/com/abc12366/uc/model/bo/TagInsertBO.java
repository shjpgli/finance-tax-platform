package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-06-15
 * Time: 10:45
 */
public class TagInsertBO {
    @NotEmpty
    @Size(max = 20)
    private String tagName;
    @NotNull
    private Boolean status;
    @NotEmpty
    @Size(max = 2)
    private String category;
    @Size(max = 100)
    private String description;
    @Size(max = 100)
    private String rule;
    @Max(9999)
    private Integer weight;

    public TagInsertBO() {
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
