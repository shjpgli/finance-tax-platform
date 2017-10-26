package com.abc12366.bangbang.model.question.bo;

/**
 * @Author liuQi
 * @Date 2017/10/26 14:49
 */
public enum QuestionTipOffStatus {

    auditing("未发布"),
    approved("已发布"),
    refuse("已撤销");

    private String description;

    QuestionTipOffStatus(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
