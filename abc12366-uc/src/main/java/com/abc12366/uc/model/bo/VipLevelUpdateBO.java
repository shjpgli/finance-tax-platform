package com.abc12366.uc.model.bo;


import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:24
 */
public class VipLevelUpdateBO {
    @Size(max = 10)
    private String level;
    private Double factor;
    private Boolean status;
    @Size(max = 10)
    private String levelCode;

    public VipLevelUpdateBO() {
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
