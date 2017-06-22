package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-22
 * Time: 16:51
 */
public class VipLevelInsertBO {
    @NotEmpty
    @Size(max = 10)
    private String level;
    private Double factor;
    @NotNull
    private Boolean status;
    @Size(max = 10)
    private String levelCode;

    public VipLevelInsertBO() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }
}
