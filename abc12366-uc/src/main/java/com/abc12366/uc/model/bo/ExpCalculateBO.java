package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 根据用户ID和经验值规则ID计算用户经验值接口入参实体类
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 10:29
 */
public class ExpCalculateBO {
    //用户ID
    @NotEmpty
    private String userId;
    //经验值规则ID
    @NotEmpty
    private String ruleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
}
