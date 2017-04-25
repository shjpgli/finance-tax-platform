package com.abc12366.admin.model.cms;
import java.io.Serializable;


/**
 *
 * CMS专题内容关联表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentTopic implements Serializable {

	/****/
	private String contentId;

	/****/
	private String topicId;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setTopicId(String topicId){
		this.topicId = topicId;
	}

	public String getTopicId(){
		return this.topicId;
	}

}
