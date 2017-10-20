package com.abc12366.bangbang.model.question;
import java.io.Serializable;


/**
 * 
 * 勋章管理表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionMedal implements Serializable {

	/**PK**/
	private String id;

	/**勋章头像**/
	private String image;

	/**勋章名称**/
	private String name;

	/**获取条件**/
	private String condition;

	/**描述**/
	private String description;

	/**启用，停用**/
	private Integer status;

	/**管理员id**/
	private String updateAdmin;

	/****/
	private java.util.Date updateTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return this.image;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setCondition(String condition){
		this.condition = condition;
	}

	public String getCondition(){
		return this.condition;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setUpdateAdmin(String updateAdmin){
		this.updateAdmin = updateAdmin;
	}

	public String getUpdateAdmin(){
		return this.updateAdmin;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
