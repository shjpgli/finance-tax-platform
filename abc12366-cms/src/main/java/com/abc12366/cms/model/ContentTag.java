package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS内容TAG表
 * 
 **/
@SuppressWarnings("serial")
public class ContentTag implements Serializable {

	/**tagId**varchar(64)**/
	private String tagId;

	/**tag名称**varchar(50)**/
	private String tagName;

	/**被引用的次数**int(11)**/
	private Integer refCounter;



	public void setTagId(String tagId){
		this.tagId = tagId;
	}

	public String getTagId(){
		return this.tagId;
	}

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return this.tagName;
	}

	public void setRefCounter(Integer refCounter){
		this.refCounter = refCounter;
	}

	public Integer getRefCounter(){
		return this.refCounter;
	}

}
