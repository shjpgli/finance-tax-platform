package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS栏目扩展属性表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelAttr implements Serializable {

	/****/
	private String channelId;

	/**名称**/
	private String attrName;

	/**值**/
	private String attrValue;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setAttrName(String attrName){
		this.attrName = attrName;
	}

	public String getAttrName(){
		return this.attrName;
	}

	public void setAttrValue(String attrValue){
		this.attrValue = attrValue;
	}

	public String getAttrValue(){
		return this.attrValue;
	}

}
