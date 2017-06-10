package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * 站点发布表
 * 
 **/
@SuppressWarnings("serial")
public class SiteIssue implements Serializable {

	/****int(11)**/
	private Integer issueId;

	/**模板名称**varchar(100)**/
	private String templateName;

	/**操作人ID**varchar(100)**/
	private String userId;

	/**操作人姓名**varchar(100)**/
	private String userName;

	/**发布状态**varchar(100)**/
	private String issueState;

	/**发布时间**date**/
	private java.util.Date issueDate;

	/**修改时间**date**/
	private java.util.Date updateDate;



	public void setIssueId(Integer issueId){
		this.issueId = issueId;
	}

	public Integer getIssueId(){
		return this.issueId;
	}

	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}

	public String getTemplateName(){
		return this.templateName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setIssueState(String issueState){
		this.issueState = issueState;
	}

	public String getIssueState(){
		return this.issueState;
	}

	public void setIssueDate(java.util.Date issueDate){
		this.issueDate = issueDate;
	}

	public java.util.Date getIssueDate(){
		return this.issueDate;
	}

	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

}
