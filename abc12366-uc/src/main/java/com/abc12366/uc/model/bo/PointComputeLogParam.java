package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 17:04
 */
public class PointComputeLogParam {
    private String userId;
    private String upointCodexId;
    private String timeType;
    private String startTime;
    private String endTime;

    public PointComputeLogParam() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpointCodexId() {
        return upointCodexId;
    }

    public void setUpointCodexId(String upointCodexId) {
        this.upointCodexId = upointCodexId;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
