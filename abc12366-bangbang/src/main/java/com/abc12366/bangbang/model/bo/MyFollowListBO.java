package com.abc12366.bangbang.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-21
 * Time: 15:20
 */
public class MyFollowListBO {
    private String followName;
    private String answerCount;
    private String askCount;
    private String fansCount;

    public MyFollowListBO() {
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public String getAskCount() {
        return askCount;
    }

    public void setAskCount(String askCount) {
        this.askCount = askCount;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }
}
