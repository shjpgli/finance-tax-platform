package com.abc12366.cms.vo;
import java.io.Serializable;


/**
 * 
 * CMS栏目表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelVO implements Serializable {

	/****/
	private String channelId;

	/**模型ID**/
	private String modelId;

	/**站点ID**/
	private String siteId;

	/**父栏目ID**/
	private String parentId;

	/**访问路径**/
	private String channelPath;

	/**排列顺序**/
	private Integer priority;

	/**是否显示**/
	private Integer isDisplay;



	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setModelId(String modelId){
		this.modelId = modelId;
	}

	public String getModelId(){
		return this.modelId;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setChannelPath(String channelPath){
		this.channelPath = channelPath;
	}

	public String getChannelPath(){
		return this.channelPath;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setIsDisplay(Integer isDisplay){
		this.isDisplay = isDisplay;
	}

	public Integer getIsDisplay(){
		return this.isDisplay;
	}

}
