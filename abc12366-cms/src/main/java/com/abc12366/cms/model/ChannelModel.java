package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * 栏目可选内容模型表
 * 
 **/
@SuppressWarnings("serial")
public class ChannelModel implements Serializable {

	/****/
	private String channelId;

	/**模型id**/
	private String modelId;

	/**内容模板**/
	private String tplContent;

	/**排序**/
	private Integer priority;



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

	public void setTplContent(String tplContent){
		this.tplContent = tplContent;
	}

	public String getTplContent(){
		return this.tplContent;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

}
