package com.abc12366.bangbang.model.bo;

/**
 * @Author liuqi
 * @Date 2017/8/7 13:45
 */
public class FeedbackParamBO {

    /* 来源类型 */
    private String sourceType;

    /* 反馈类型 */
    private String feedbackType;


    public FeedbackParamBO(String sourceType, String feedbackType) {
        this.sourceType = sourceType;
        this.feedbackType = feedbackType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
}
