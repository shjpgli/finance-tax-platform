package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 17:04
 */
public class ExpComputeLogParam {
    private String userId;
    private String uexpCodexId;
    private String timeType;
    private String starTime;
    private String endTime;

    public ExpComputeLogParam() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUexpCodexId() {
        return uexpCodexId;
    }

    public void setUexpCodexId(String uexpCodexId) {
        this.uexpCodexId = uexpCodexId;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
