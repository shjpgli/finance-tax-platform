package com.abc12366.gateway.model.bo;
import java.io.Serializable;


/**
 *
 * 接入应用表
 *
 **/
@SuppressWarnings("serial")
public class AppBO implements Serializable {

	/**appId**/
	private String id;

	/**授权应用名称**/
	private String name;

	/**授权应用密码**/
	private String password;

	/**访问授权码**/
	private String accessToken;

	/**上次重置授权码时间**/
	private java.util.Date lastResetTokenTime;

	/**授权时间起**/
	private java.util.Date startTime;

	/**授权时间止**/
	private java.util.Date endTime;

	/**状态**/
	private Boolean status;

	/**备注**/
	private String remark;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date lastUpdate;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return this.accessToken;
	}

	public void setLastResetTokenTime(java.util.Date lastResetTokenTime){
		this.lastResetTokenTime = lastResetTokenTime;
	}

	public java.util.Date getLastResetTokenTime(){
		return this.lastResetTokenTime;
	}

	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}

	public java.util.Date getStartTime(){
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	public java.util.Date getEndTime(){
		return this.endTime;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
		return this.status;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setLastUpdate(java.util.Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public java.util.Date getLastUpdate(){
		return this.lastUpdate;
	}

}
