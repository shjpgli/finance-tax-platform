package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS栏目用户关联表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelUser implements Serializable {

	/****/
	private String channelId;

	/****/
	private String userId;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

}
