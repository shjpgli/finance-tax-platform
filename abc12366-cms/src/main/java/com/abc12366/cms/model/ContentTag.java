package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS内容标签关联表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentTag implements Serializable {

	/****/
	private String contentId;

	/****/
	private String tagId;

	/****/
	private Integer priority;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setTagId(String tagId){
		this.tagId = tagId;
	}

	public String getTagId(){
		return this.tagId;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

}
