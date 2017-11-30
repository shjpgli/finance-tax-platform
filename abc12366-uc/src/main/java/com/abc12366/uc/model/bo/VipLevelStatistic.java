package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-11-30
 * Time: 11:16
 */
public class VipLevelStatistic {
    private String levelCode;
    private int all;
    private int increase;
    private String increasePercent;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getIncreasePercent() {
        return increasePercent;
    }

    public void setIncreasePercent(String increasePercent) {
        this.increasePercent = increasePercent;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }

    @Override
    public String toString() {
        return "VipLevelStatistic{" +
                "levelCode='" + levelCode + '\'' +
                ", all=" + all +
                ", increase=" + increase +
                ", increasePercent='" + increasePercent + '\'' +
                '}';
    }
}
