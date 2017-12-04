package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-11-30
 * Time: 11:16
 */
public class ExpLevelStatistic {
    private String levelCode;
    private String levelName;
    private int all;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "ExpLevelStatistic{" +
                "levelCode='" + levelCode + '\'' +
                ", levelName='" + levelName + '\'' +
                ", all=" + all +
                ", increasePercent='" + increasePercent + '\'' +
                '}';
    }
}
