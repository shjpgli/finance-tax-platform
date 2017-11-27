package com.abc12366.uc.model.bo;

import java.util.List;

/**
 * 用户活跃度统计bo
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-11-24
 * Time: 16:25
 */
public class UserLivenessYearBO {
    /**
     * 月份
     */
    private String month;

    /**
     * 每月统计数据
     */
    private List<UserLivenessMonthBO> userLivenessMonthBOList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<UserLivenessMonthBO> getUserLivenessMonthBOList() {
        return userLivenessMonthBOList;
    }

    public void setUserLivenessMonthBOList(List<UserLivenessMonthBO> userLivenessMonthBOList) {
        this.userLivenessMonthBOList = userLivenessMonthBOList;
    }

    @Override
    public String toString() {
        return "UserLivenessYearBO{" +
                "month='" + month + '\'' +
                ", userLivenessMonthBOList=" + userLivenessMonthBOList +
                '}';
    }
}
