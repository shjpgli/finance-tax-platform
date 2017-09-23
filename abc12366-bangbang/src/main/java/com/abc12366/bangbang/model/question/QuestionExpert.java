package com.abc12366.bangbang.model.question;

/**
 * 
 * 
 * 
 **/
public class QuestionExpert {

	/**PK**/
	private String id;

	/**uc关联用户id**/
	private String useId;

	/**专家头像**/
	private String image;

	/**擅长领域（对应字典表的bb_question_expert_expertise编码）**/
	private String expertise;

	/**权限（对应字典表的bb_question_expert_permission编码）**/
	private String permission;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date updateTime;

	/**对应uc_admin用户**/
	private String createUser;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUseId(String useId){
		this.useId = useId;
	}

	public String getUseId(){
		return this.useId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return this.image;
	}

	public void setExpertise(String expertise){
		this.expertise = expertise;
	}

	public String getExpertise(){
		return this.expertise;
	}

	public void setPermission(String permission){
		this.permission = permission;
	}

	public String getPermission(){
		return this.permission;
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

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
	}


}
