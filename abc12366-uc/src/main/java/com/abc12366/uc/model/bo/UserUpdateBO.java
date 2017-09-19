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
    @Size(min = 6, max = 32, message = "用户名长度只能在6到32之间")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,32}$", message = "用户名只能为数字字母组合！")
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
    private Integer points;
    private Integer exp;
    private String vipLevel;
    private Boolean status;
    private Date createTime;
    private Date lastUpdate;
    private String wxopenid;
    private String wxheadimg;
    private String wxnickname;
    
    
    public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}

	public String getWxheadimg() {
		return wxheadimg;
	}

	public void setWxheadimg(String wxheadimg) {
		this.wxheadimg = wxheadimg;
	}

	public String getWxnickname() {
		return wxnickname;
	}

	public void setWxnickname(String wxnickname) {
		this.wxnickname = wxnickname;
	}

	public UserUpdateBO() {
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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
}
