package com.abc12366.gateway.model.bo;
import java.io.Serializable;


/**
 *
 * 接口调用日志表
 *
 **/
@SuppressWarnings("serial")
public class BlacklistBO implements Serializable {

	/**ID**/
	private String id;

	/**userId**/
	private String userId;

	/**IP地址**/
	private String ip;

	/**锁定开始时间**/
	private java.util.Date startTime;

	/**锁定结束时间**/
	private java.util.Date endTime;

	/**状态（1正常、0锁定）**/
	private Boolean status;

	/**备注**/
	private String remark;

	/**创建时间**/
	private java.util.Date createTime;

	/**创建人ID**/
	private String createUser;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
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

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
	}

}
