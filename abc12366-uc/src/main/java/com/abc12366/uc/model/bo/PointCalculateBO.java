package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 10:29
 */
public class PointCalculateBO {
    //用户ID
    private String userId;

    //积分规则ID
    private String ruleCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Override
    public String toString() {
        return "PointCalculateBO{" +
                "userId='" + userId + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                '}';
    }
}
