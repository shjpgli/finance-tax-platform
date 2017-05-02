package com.abc12366.admin.model;
import java.io.Serializable;


/**
 * 
 * 系统用户表
 * 
 **/
@SuppressWarnings("serial")
public class User implements Serializable {

	/**ID**/
	private String id;

	/**用户名**/
	private String username;

	/**密码**/
	private String password;

	/**昵称**/
	private String nickname;

	/**用户状态**/
	private Integer status;

	/**用户令牌**/
	private String token;

	/**最后重置token时间**/
	private java.util.Date lastResetTokenTime;

	/**创建时间**/
	private java.util.Date createTime;

	/**最后修改时间**/
	private java.util.Date lastUpdate;

	private String organizationId;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return this.nickname;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return this.token;
	}

	public void setLastResetTokenTime(java.util.Date lastResetTokenTime){
		this.lastResetTokenTime = lastResetTokenTime;
	}

	public java.util.Date getLastResetTokenTime(){
		return this.lastResetTokenTime;
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
