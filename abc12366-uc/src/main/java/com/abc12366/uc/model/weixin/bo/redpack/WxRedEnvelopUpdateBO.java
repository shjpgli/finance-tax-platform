package com.abc12366.uc.model.weixin.bo.redpack;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 微信红包修改口令记录BO
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-11-02 4:30 PM
 * @since 1.0.0
 */
public class WxRedEnvelopUpdateBO {

    /**
     * 口令ID
     */
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 微信OpenID
     */
    @Length(min = 16, max = 64)
    private String openId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "WxRedEnvelopUpdateBO{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", openId='" + openId + '\'' +
                '}';
    }
}
