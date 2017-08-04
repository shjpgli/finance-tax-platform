package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:38
 */
public class VipLogBO {
    private String id;
    private String userId;
    private String source;
    private String levelId;
    private Date createTime;

    public VipLogBO() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VipLogBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", source='" + source + '\'' +
                ", levelId='" + levelId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
