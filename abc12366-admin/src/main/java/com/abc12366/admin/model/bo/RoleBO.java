package com.abc12366.admin.model.bo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 
 * 系统角色表
 * 
 **/
@SuppressWarnings("serial")
public class RoleBO implements Serializable {

	private String id;

	@NotEmpty
    @Size(min = 1, max = 50, message = "角色名称必须为1-50位")
	private String roleName;

	private String remark;

	private java.util.Date createTime;

	private java.util.Date updateTime;

    @NotNull
    private Boolean status;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
