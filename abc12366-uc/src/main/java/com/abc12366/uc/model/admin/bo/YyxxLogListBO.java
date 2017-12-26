package com.abc12366.uc.model.admin.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-20
 * Time: 16:10
 */
public class YyxxLogListBO {
    private String id;
    private String userId;
    private String messageId;
    private String type;
    private Date createTime;
    private String nickName;

    public YyxxLogListBO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "YyxxLogListBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
