package com.abc12366.cszj.model.weixin.bo.gzh;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 公众号信息
 * @author zhushuai 2017-8-1
 *
 */
public class GzhInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	@NotEmpty
	private String gzhName;
	@NotEmpty
	private String appid;
	@NotEmpty
	private String secret;
	@NotEmpty
	private String tokenStr;
	@NotEmpty
	private String serverUrl;
	private Date creatDate;
	private Date lastupdate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGzhName() {
		return gzhName;
	}
	public void setGzhName(String gzhName) {
		this.gzhName = gzhName;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getTokenStr() {
		return tokenStr;
	}
	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	
	
	
    
}
