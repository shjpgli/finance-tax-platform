package com.abc12366.uc.model.bo;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:14 PM
 * @since 1.0.0
 */
public class UserBO {
    private String id;
    private String username;
    private String phone;
    private String password;
    private String regMail;
    private String userPicturePath;
    private String regIP;
    private String salt;
    private String realName;
    private boolean status;
    private Date createTime;
    private Date lastUpdate;

    public UserBO() {
    }

    public UserBO(String id, String username, String phone, String password, String regMail, String userPicturePath, String regIP, String salt, String realName, boolean status, Date createTime, Date lastUpdate) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.regMail = regMail;
        this.userPicturePath = userPicturePath;
        this.regIP = regIP;
        this.salt = salt;
        this.realName = realName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegMail() {
        return regMail;
    }

    public void setRegMail(String regMail) {
        this.regMail = regMail;
    }

    public String getUserPicturePath() {
        return userPicturePath;
    }

    public void setUserPicturePath(String userPicturePath) {
        this.userPicturePath = userPicturePath;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
        return "UserBO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", regMail='" + regMail + '\'' +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", regIP='" + regIP + '\'' +
                ", salt='" + salt + '\'' +
                ", realName='" + realName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
