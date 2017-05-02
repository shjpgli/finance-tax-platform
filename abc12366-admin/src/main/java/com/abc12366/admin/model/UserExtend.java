package com.abc12366.admin.model;
import java.io.Serializable;


/**
 * 
 * 系统用户扩展表
 * 
 **/
@SuppressWarnings("serial")
public class UserExtend implements Serializable {

	/**ID**/
	private String id;

	/**用户ID**/
	private String userId;

	/**组织机构ID**/
	private String orgId;

	/**职务**/
	private String job;

	/**联系方式**/
	private String phone;

	/**联系地址**/
	private String address;

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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setOrgId(String orgId){
		this.orgId = orgId;
	}

	public String getOrgId(){
		return this.orgId;
	}

	public void setJob(String job){
		this.job = job;
	}

	public String getJob(){
		return this.job;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
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
