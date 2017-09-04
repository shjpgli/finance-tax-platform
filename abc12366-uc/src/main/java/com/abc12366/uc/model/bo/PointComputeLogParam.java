package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 17:04
 */
public class PointComputeLogParam {
    private String userId;
    private String upointCodexId;
    private String timeType;
    private Date startTime;
    private Date endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
