package com.abc12366.admin.model.cms;
import java.io.Serializable;


/**
 *
 * CMS内容栏目关联表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentChannel implements Serializable {

	/****/
	private String channelId;

	/****/
	private String contentId;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

}
