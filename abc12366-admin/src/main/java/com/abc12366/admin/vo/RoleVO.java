package com.abc12366.admin.vo;
import java.io.Serializable;


/**
 * 
 * 系统角色表
 * 
 **/
@SuppressWarnings("serial")
public class RoleVO implements Serializable {

	/**ID**/
	private String id;

	/**角色名称**/
	private String roleName;

	/**备注**/
	private String remark;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date updateTime;

	/**状态**/
	private Integer status;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return this.roleName;
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

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
