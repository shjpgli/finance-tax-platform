package com.abc12366.bangbang.model.question;


/**
 * 
 * 帮派等级
 * 
 **/
public class QuestionFactionLevel  {

	/**PK**/
	private String id;

	/**帮派等级名称**/
	private String name;

	/**荣誉值**/
	private Integer honorValue;

	/****/
	private java.util.Date createTime;

	/****/
	private String createAdmin;

	/****/
	private java.util.Date updateTime;

	/****/
	private String updateAdmin;



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

	public void setHonorValue(Integer honorValue){
		this.honorValue = honorValue;
	}

	public Integer getHonorValue(){
		return this.honorValue;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateAdmin(String createAdmin){
		this.createAdmin = createAdmin;
	}

	public String getCreateAdmin(){
		return this.createAdmin;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateAdmin(String updateAdmin){
		this.updateAdmin = updateAdmin;
	}

	public String getUpdateAdmin(){
		return this.updateAdmin;
	}

}
