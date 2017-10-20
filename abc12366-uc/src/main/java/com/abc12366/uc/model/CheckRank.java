package com.abc12366.uc.model;

import java.util.Date;

/**
 * 用户签到排行返回参数实体类
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-21
 * Time: 17:00
 */
public class CheckRank {
    private String id;
    //用户ID
    private String userId;
    //用户头像图片路径
    private String userPicturePath;
    //用户昵称
    private String nickname;
    //最后更新时间
    private Date lastUpdate;
    //年内累计签到统计
    private Integer count;
    //年份
    private String year;

    public CheckRank() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPicturePath() {
        return userPicturePath;
    }

    public void setUserPicturePath(String userPicturePath) {
        this.userPicturePath = userPicturePath;
    }

    @Override
    public String toString() {
        return "CheckRank{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", count=" + count +
                ", year='" + year + '\'' +
                '}';
    }
}
