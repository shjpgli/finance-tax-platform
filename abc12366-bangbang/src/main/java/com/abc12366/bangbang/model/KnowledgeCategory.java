package com.abc12366.bangbang.model;
import java.io.Serializable;


/**
 * @Author liuqi
 * @Date 2017/8/2 17:58
 * 知识库分类表
 **/
public class KnowledgeCategory{

	/**知识库的分类ID**/
	private String id;

	/**分类名称**/
	private String name;

	/**标识编码**/
	private String code;

	/**父类标识编码**/
	private String parentCode;

	/**顺序**/
	private Integer sort;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date updateTime;

	/**创建人**/
	private String createUser;

	/**修改人**/
	private String updateUser;



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

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	public String getParentCode(){
		return this.parentCode;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
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

	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

	public String getUpdateUser(){
		return this.updateUser;
	}

}