package com.abc12366.cms.model.questionnaire;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class AccessLog implements Serializable {

	/**PK**/
	private String id;

	/**问卷ID**/
	private String questionId;

	/**用户ID**/
	private String userId;

	/**接入终端**/
	private String accessTerminal;

	/**IP地址**/
	private String ipAddress;

	/**访问时间**/
	private java.util.Date createTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setAccessTerminal(String accessTerminal){
		this.accessTerminal = accessTerminal;
	}

	public String getAccessTerminal(){
		return this.accessTerminal;
	}

	public void setIpAddress(String ipAddress){
		this.ipAddress = ipAddress;
	}

	public String getIpAddress(){
		return this.ipAddress;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

}
