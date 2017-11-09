package com.abc12366.uc.model.bo;

/**
 * 用户简单信息实体类：用户ID，用户昵称，用户头像，擅长领域
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-12
 * Time: 10:31
 */
public class UserSimpleInfoBO {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像图片路径
     */
    private String userPicturePath;
    /**
     * 用户标签
     */
    private String tags;

    public UserSimpleInfoBO() {
    }

    public UserSimpleInfoBO(String id, String nickName, String userPicturePath, String tags) {
        this.id = id;
        this.nickName = nickName;
        this.userPicturePath = userPicturePath;
        this.tags = tags;
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

    public String getUserPicturePath() {
        return userPicturePath;
    }

    public void setUserPicturePath(String userPicturePath) {
        this.userPicturePath = userPicturePath;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "UserSimpleInfoBO{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
