package com.abc12366.cszj.model.weixin.bo.person;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * Created by zhushuai on 2017/6/14
 *
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String phone;
	private String regMail;
	private String regIP;
	private String nickname;
	private String userPicturePath;
	private String maxUserPicturePath;
	private String midUserPicturePath;
	private String minUserPicturePath;
	private String points;
	private String exp;
	private String vipLevel;
	private boolean status;
	private Date createTime;
	private Date lastUpdate;	
	private String realName;
    private String levelName;
    private String medal;
    private String medalIcon;

    
    
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getMedal() {
		return medal;
	}
	public void setMedal(String medal) {
		this.medal = medal;
	}
	public String getMedalIcon() {
		return medalIcon;
	}
	public void setMedalIcon(String medalIcon) {
		this.medalIcon = medalIcon;
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
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public boolean getStatus() {
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
    
}
