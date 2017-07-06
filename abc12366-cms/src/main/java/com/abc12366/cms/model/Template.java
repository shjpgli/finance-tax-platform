package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS模板表
 * 
 **/
@SuppressWarnings("serial")
public class Template implements Serializable {

	/**模板ID**varchar(64)**/
	private String templateId;

	/**模板中文名称**varchar(100)**/
	private String templateName;

	/**父节点路径**varchar(200)**/
	private String parentPath;

	/**模板路径**varchar(200)**/
	private String templatePath;

	/**是否为文件夹**tinyint(1)**/
	private Integer isFolder;

	/**模板属性（如：栏目、内容、专题）**varchar(100)**/
	private String templateProperty;

	/**站点ID**varchar(64)**/
	private String siteId;

	/**排序号**tinyint(11)**/
	private Integer priority;

	/**启停标志位**tinyint(1)**/
	private Integer state;

	/**模板版本号**varchar(100)**/
	private String versions;

	/**模板新建时间**datetime**/
	private java.util.Date createTime;

	/**模板修改时间**datetime**/
	private java.util.Date updateTime;



	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return this.templateId;
	}

	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}

	public String getTemplateName(){
		return this.templateName;
	}

	public void setParentPath(String parentPath){
		this.parentPath = parentPath;
	}

	public String getParentPath(){
		return this.parentPath;
	}

	public void setTemplatePath(String templatePath){
		this.templatePath = templatePath;
	}

	public String getTemplatePath(){
		return this.templatePath;
	}

	public void setIsFolder(Integer isFolder){
		this.isFolder = isFolder;
	}

	public Integer getIsFolder(){
		return this.isFolder;
	}

	public void setTemplateProperty(String templateProperty){
		this.templateProperty = templateProperty;
	}

	public String getTemplateProperty(){
		return this.templateProperty;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return this.state;
	}

	public void setVersions(String versions){
		this.versions = versions;
	}

	public String getVersions(){
		return this.versions;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
