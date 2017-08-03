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

	/**父类ID**/
	private String parentId;

	/**级别**/
	private Integer level;



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

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

}
