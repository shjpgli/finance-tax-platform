package com.abc12366.bangbang.model.bo;
import java.io.Serializable;


/**
 * 
 * 维基百科表
 * 
 **/
@SuppressWarnings("serial")
public class WikiBO implements Serializable {

	/**PK**/
	private String id;

	/**主题名称**/
	private String subject;

	/**分类**/
	private String type;

	/**内容**/
	private String content;

	/**状态**/
	private Boolean status;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date lastUpdate;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return this.subject;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
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
