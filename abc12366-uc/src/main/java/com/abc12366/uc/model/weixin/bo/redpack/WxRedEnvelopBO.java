package com.abc12366.uc.model.weixin.bo.redpack;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 微信红包口令BO
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-10-18 7:01 PM
 * @since 1.0.0
 */
public class WxRedEnvelopBO {

    /**
     * （主键）
     */
    private String id;

    /**
     * 红包口令
     */
    @NotEmpty
    @Length(min = 6, max = 32)
    private String secret;

    /**
     * 口令产生时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 红包活动编号（主键）
     */
    @NotEmpty
    @Length(min = 16, max = 64)
    private String activityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "WxRedEnvelopBO{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", createTime=" + createTime +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
