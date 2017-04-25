package com.abc12366.admin.model.cms;
import java.io.Serializable;


/**
 * 
 * CMS专题表
 * 
 **/
@SuppressWarnings("serial")
public class Topic implements Serializable {

	/****/
	private String topicId;

	/****/
	private String channelId;

	/**名称**/
	private String topicName;

	/**简称**/
	private String shortName;

	/**关键字**/
	private String keywords;

	/**描述**/
	private String description;

	/**标题图**/
	private String titleImg;

	/**内容图**/
	private String contentImg;

	/**专题模板**/
	private String tplContent;

	/**排列顺序**/
	private Integer priority;

	/**是否推??**/
	private Integer isRecommend;



	public void setTopicId(String topicId){
		this.topicId = topicId;
	}

	public String getTopicId(){
		return this.topicId;
	}

	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return this.channelId;
	}

	public void setTopicName(String topicName){
		this.topicName = topicName;
	}

	public String getTopicName(){
		return this.topicName;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return this.shortName;
	}

	public void setKeywords(String keywords){
		this.keywords = keywords;
	}

	public String getKeywords(){
		return this.keywords;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setTitleImg(String titleImg){
		this.titleImg = titleImg;
	}

	public String getTitleImg(){
		return this.titleImg;
	}

	public void setContentImg(String contentImg){
		this.contentImg = contentImg;
	}

	public String getContentImg(){
		return this.contentImg;
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

	public void setIsRecommend(Integer isRecommend){
		this.isRecommend = isRecommend;
	}

	public Integer getIsRecommend(){
		return this.isRecommend;
	}

}
