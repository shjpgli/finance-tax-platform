package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS栏目表
 * 
 **/
@SuppressWarnings("serial")
public class Channel implements Serializable {

	/**channelId**varchar(64)**/
	private String channelId;

	/**模型ID**varchar(64)**/
	private String modelId;

	/**站点ID**varchar(64)**/
	private String siteId;

	/**父栏目ID**varchar(64)**/
	private String parentId;

	/**访问路径**varchar(30)**/
	private String channelPath;

	/**排列顺序**int(11)**/
	private Integer priority;

	/**是否显示**tinyint(1)**/
	private Integer isDisplay;

	/**栏目名称**varchar(100)**/
	private String channelName;



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

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public String getChannelName(){
		return this.channelName;
	}

}
