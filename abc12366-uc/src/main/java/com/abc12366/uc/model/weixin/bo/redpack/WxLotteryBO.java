package com.abc12366.uc.model.weixin.bo.redpack;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 4:30 PM
 * @since 1.0.0
 */
public class WxLotteryBO {

    @NotEmpty
    @Length(min = 16, max = 64)
    private String activityId;

    @NotEmpty
    @Length(min = 2, max = 32)
    private String secret;

    @NotEmpty
    @Length(min = 16, max = 64)
    private String openId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "LotteryBO{" +
                "activityId='" + activityId + '\'' +
                ", secret='" + secret + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
