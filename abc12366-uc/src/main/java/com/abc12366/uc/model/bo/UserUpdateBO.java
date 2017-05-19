package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-28 4:34 PM
 * @since 1.0.0
 */
public class UserUpdateBO {

    private String id;
    @Size(min = 6, max = 32)
    private String username;
    @Pattern(regexp = "^\\d{11}$")
    @Size(min = 11, max = 11)
    private String phone;
    @Email
    private String regMail;
    private String regIP;
    private String nickname;
    private String userPicturePath;
    private String maxUserPicturePath;
    private String midUserPicturePath;
    private String minUserPicturePath;
    private int points;
    private int exp;
    private int vipLevel;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    public UserUpdateBO() {
    }

    public UserUpdateBO(String id, String username, String phone, String regMail, String regIP, String nickname, String userPicturePath, String maxUserPicturePath, String midUserPicturePath, String minUserPicturePath, int points, int exp, int vipLevel, boolean status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.regMail = regMail;
        this.regIP = regIP;
        this.nickname = nickname;
        this.userPicturePath = userPicturePath;
        this.maxUserPicturePath = maxUserPicturePath;
        this.midUserPicturePath = midUserPicturePath;
        this.minUserPicturePath = minUserPicturePath;
        this.points = points;
        this.exp = exp;
        this.vipLevel = vipLevel;
        this.status = status;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegMail() {
        return regMail;
    }

    public void setRegMail(String regMail) {
        this.regMail = regMail;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
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

    public String getMaxUserPicturePath() {
        return maxUserPicturePath;
    }

    public void setMaxUserPicturePath(String maxUserPicturePath) {
        this.maxUserPicturePath = maxUserPicturePath;
    }

    public String getMidUserPicturePath() {
        return midUserPicturePath;
    }

    public void setMidUserPicturePath(String midUserPicturePath) {
        this.midUserPicturePath = midUserPicturePath;
    }

    public String getMinUserPicturePath() {
        return minUserPicturePath;
    }

    public void setMinUserPicturePath(String minUserPicturePath) {
        this.minUserPicturePath = minUserPicturePath;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "UserUpdateBO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", regMail='" + regMail + '\'' +
                ", regIP='" + regIP + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", maxUserPicturePath='" + maxUserPicturePath + '\'' +
                ", midUserPicturePath='" + midUserPicturePath + '\'' +
                ", minUserPicturePath='" + minUserPicturePath + '\'' +
                ", points=" + points +
                ", exp=" + exp +
                ", vipLevel=" + vipLevel +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
