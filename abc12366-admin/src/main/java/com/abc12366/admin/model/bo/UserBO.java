package com.abc12366.admin.model.bo;
import com.abc12366.admin.model.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * 系统用户表
 * 
 **/
@SuppressWarnings("serial")
public class UserBO implements Serializable {

	/**ID**/
	private String id;

	/**用户名**/
    @NotEmpty
    @Size(min = 6, max = 32, message = "用户名称必须为6-32位")
	private String username;

    @NotEmpty
    @Size(min = 6, max = 32, message = "用户密码必须为6-32位")
	/**密码**/
	private String password;

	/**昵称**/
	private String nickname;

	/**用户状态**/
	private Integer status;

	/**创建时间**/
	private java.util.Date createTime;

	/**最后修改时间**/
	private java.util.Date lastUpdate;

	private String roleIds;

	private List<Role> rolesList;

    private String organizationId;

    private String organizationName;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

	public List<Role> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
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
