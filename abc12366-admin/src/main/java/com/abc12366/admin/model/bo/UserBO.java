package com.abc12366.admin.model.bo;
import com.abc12366.admin.model.LoginInfo;
import com.abc12366.admin.model.Menu;
import com.abc12366.admin.model.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 
 * 系统用户表
 * 
 **/
@SuppressWarnings("serial")
public class UserBO extends UserExtendBO implements Serializable {

	/**ID**/
	private String id;

	/**用户名**/
    @NotEmpty
    @Size(min = 6, max = 32)
	private String username;

    @NotEmpty
    @Size(min = 6, max = 32)
	/**密码**/
	private String password;

	/**昵称**/
	private String nickname;

	/**用户状态**/
	private Boolean status;

	private String roleIds;

	private List<Role> rolesList;

    private LoginInfo loginInfo;

    private Map<String,List<Menu>> menuMap;

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

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public Map<String, List<Menu>> getMenuMap() {
        return menuMap;
    }

    public void setMenuMap(Map<String, List<Menu>> menuMap) {
        this.menuMap = menuMap;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
