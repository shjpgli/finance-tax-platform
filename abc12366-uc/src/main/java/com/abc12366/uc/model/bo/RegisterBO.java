package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:18 PM
 * @since 1.0.0
 */
public class RegisterBO implements Serializable {

    @NotEmpty
    @Pattern(regexp = "^\\d{11}$")
    @Size(min = 11, max = 11)
    private String phone;

    @NotEmpty
    private String password;

    @Email
    private String regMail;
    private String username;
    @Size(max = 50)
    @Pattern(regexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")
    private String regIP;
    private String salt;
    @Size(max = 32)
    private String nickname;
    @NotNull
    private Boolean status;
    @Size(max = 100)
    private String userPicturePath;
    @Size(max = 100)
    private String maxUserPicturePath;
    @Size(max = 100)
    private String midUserPicturePath;
    @Size(max = 100)
    private String minUserPicturePath;
    @NotEmpty
    private String verifyingCode;
    @NotEmpty
    private String verifyingType;
    private String province;
    private String city;

    public RegisterBO() {
    }

    public String getVerifyingType() {
        return verifyingType;
    }

    public void setVerifyingType(String verifyingType) {
        this.verifyingType = verifyingType;
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

    public String getVerifyingCode() {
        return verifyingCode;
    }

    public void setVerifyingCode(String verifyingCode) {
        this.verifyingCode = verifyingCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "RegisterBO{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", regMail='" + regMail + '\'' +
                ", username='" + username + '\'' +
                ", regIP='" + regIP + '\'' +
                ", salt='" + salt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                ", userPicturePath='" + userPicturePath + '\'' +
                ", maxUserPicturePath='" + maxUserPicturePath + '\'' +
                ", midUserPicturePath='" + midUserPicturePath + '\'' +
                ", minUserPicturePath='" + minUserPicturePath + '\'' +
                ", verifyingCode='" + verifyingCode + '\'' +
                ", verifyingType='" + verifyingType + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
