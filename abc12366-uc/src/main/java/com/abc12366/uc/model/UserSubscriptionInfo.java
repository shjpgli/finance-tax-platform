package com.abc12366.uc.model;

import java.util.Date;

/**
 * 个人消息订阅实体类
 * @author Administrator
 *
 */
public class UserSubscriptionInfo {
	
	private String id;

	private String userId;  //用户id

	private String settingId; //消息订阅id

	private Boolean web; //是否订阅站内消息

	private Boolean wechat;  //是否订阅微信消息
	
	private Boolean message;  //是否订阅短信消息
	
	private Date createTime;  //创建时间
	
	private Date lastUpdate;  //更新时间
	
	private String type; // 消息类型

	private String busiType; // 业务类型

	private Boolean hasWeb; // 是否有站内消息

	private Boolean webForce; // 是否强制订阅站内消息

	private Boolean hasWechat; // 是否有微信消息

	private Boolean wechatForce; // 是否强制订阅微信消息

	private Boolean hasMessage; // 是否有短信消息

	private Boolean messageForce; // 是否强制订阅短信

	
	
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

	public String getSettingId() {
		return settingId;
	}

	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}

	public Boolean getWeb() {
		return web;
	}

	public void setWeb(Boolean web) {
		this.web = web;
	}

	public Boolean getWechat() {
		return wechat;
	}

	public void setWechat(Boolean wechat) {
		this.wechat = wechat;
	}

	public Boolean getMessage() {
		return message;
	}

	public void setMessage(Boolean message) {
		this.message = message;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public Boolean getHasWeb() {
		return hasWeb;
	}

	public void setHasWeb(Boolean hasWeb) {
		this.hasWeb = hasWeb;
	}

	public Boolean getWebForce() {
		return webForce;
	}

	public void setWebForce(Boolean webForce) {
		this.webForce = webForce;
	}

	public Boolean getHasWechat() {
		return hasWechat;
	}

	public void setHasWechat(Boolean hasWechat) {
		this.hasWechat = hasWechat;
	}

	public Boolean getWechatForce() {
		return wechatForce;
	}

	public void setWechatForce(Boolean wechatForce) {
		this.wechatForce = wechatForce;
	}

	public Boolean getHasMessage() {
		return hasMessage;
	}

	public void setHasMessage(Boolean hasMessage) {
		this.hasMessage = hasMessage;
	}

	public Boolean getMessageForce() {
		return messageForce;
	}

	public void setMessageForce(Boolean messageForce) {
		this.messageForce = messageForce;
	}
	
	
	
}
