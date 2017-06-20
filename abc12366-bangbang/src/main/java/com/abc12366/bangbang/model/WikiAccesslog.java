package com.abc12366.bangbang.model;
import java.io.Serializable;


/**
 * 
 * 维基百科访问日志表
 * 
 **/
@SuppressWarnings("serial")
public class WikiAccesslog implements Serializable {

	/**PK**/
	private String id;

	/**FK**/
	private String wikiId;

	/**FK**/
	private String userId;

	/**是否有帮助**/
	private Boolean helpful;

	/****/
	private java.util.Date createTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setWikiId(String wikiId){
		this.wikiId = wikiId;
	}

	public String getWikiId(){
		return this.wikiId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setHelpful(Boolean helpful){
		this.helpful = helpful;
	}

	public Boolean getHelpful(){
		return this.helpful;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

}
