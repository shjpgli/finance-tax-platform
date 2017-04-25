package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS模型表
 * 
 **/
@SuppressWarnings("serial")
public class Model implements Serializable {

	/****/
	private String modelId;

	/**名称**/
	private String modelName;

	/**路径**/
	private String modelPath;

	/**栏目模板前缀**/
	private String tplChannelPrefix;

	/**内容模板前缀**/
	private String tplContentPrefix;

	/**栏目标题图宽度**/
	private Integer titleImgWidth;

	/**栏目标题图高度**/
	private Integer titleImgHeight;

	/**栏目内容图宽度**/
	private Integer contentImgWidth;

	/**栏目内容图高度**/
	private Integer contentImgHeight;

	/**排列顺序**/
	private Integer priority;

	/**是否有内容**/
	private Integer hasContent;

	/**是否禁用**/
	private Integer isDisabled;

	/**是否默认模型**/
	private Integer isDef;

	/**是否全站模型**/
	private Integer isGlobal;

	/**非全站模型所属站点**/
	private Integer siteId;



	public void setModelId(String modelId){
		this.modelId = modelId;
	}

	public String getModelId(){
		return this.modelId;
	}

	public void setModelName(String modelName){
		this.modelName = modelName;
	}

	public String getModelName(){
		return this.modelName;
	}

	public void setModelPath(String modelPath){
		this.modelPath = modelPath;
	}

	public String getModelPath(){
		return this.modelPath;
	}

	public void setTplChannelPrefix(String tplChannelPrefix){
		this.tplChannelPrefix = tplChannelPrefix;
	}

	public String getTplChannelPrefix(){
		return this.tplChannelPrefix;
	}

	public void setTplContentPrefix(String tplContentPrefix){
		this.tplContentPrefix = tplContentPrefix;
	}

	public String getTplContentPrefix(){
		return this.tplContentPrefix;
	}

	public void setTitleImgWidth(Integer titleImgWidth){
		this.titleImgWidth = titleImgWidth;
	}

	public Integer getTitleImgWidth(){
		return this.titleImgWidth;
	}

	public void setTitleImgHeight(Integer titleImgHeight){
		this.titleImgHeight = titleImgHeight;
	}

	public Integer getTitleImgHeight(){
		return this.titleImgHeight;
	}

	public void setContentImgWidth(Integer contentImgWidth){
		this.contentImgWidth = contentImgWidth;
	}

	public Integer getContentImgWidth(){
		return this.contentImgWidth;
	}

	public void setContentImgHeight(Integer contentImgHeight){
		this.contentImgHeight = contentImgHeight;
	}

	public Integer getContentImgHeight(){
		return this.contentImgHeight;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setHasContent(Integer hasContent){
		this.hasContent = hasContent;
	}

	public Integer getHasContent(){
		return this.hasContent;
	}

	public void setIsDisabled(Integer isDisabled){
		this.isDisabled = isDisabled;
	}

	public Integer getIsDisabled(){
		return this.isDisabled;
	}

	public void setIsDef(Integer isDef){
		this.isDef = isDef;
	}

	public Integer getIsDef(){
		return this.isDef;
	}

	public void setIsGlobal(Integer isGlobal){
		this.isGlobal = isGlobal;
	}

	public Integer getIsGlobal(){
		return this.isGlobal;
	}

	public void setSiteId(Integer siteId){
		this.siteId = siteId;
	}

	public Integer getSiteId(){
		return this.siteId;
	}

}
