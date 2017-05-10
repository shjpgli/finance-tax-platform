package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:18 PM
 * @since 1.0.0
 */
public class RegisterBO implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 6, max = 32, message = "用户名必须6-32位")
    private String username;

    @NotEmpty(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号必须为11为数字")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码必须8-32位")
    private String password;

    private String regMail;
    private String realName;
    private String userPicturePath;
    private String regIP;
    private String salt;

    public String getRegMail() {
        return regMail;
    }

    public void setRegMail(String regMail) {
        this.regMail = regMail;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public RegisterBO(String username, String phone, String password, String regMail, String realName, String userPicturePath, String regIP, String salt) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.regMail = regMail;
        this.realName = realName;
        this.userPicturePath = userPicturePath;
        this.regIP = regIP;
        this.salt = salt;
    }

    public RegisterBO() {
    }

    public RegisterBO(String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;
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

    @Override
    public String toString() {
        return "RegisterBO{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", regMail='" + regMail + '\'' +
                ", realName='" + realName + '\'' +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", regIP='" + regIP + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
