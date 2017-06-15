package com.abc12366.cms.model.questionnaire.bo;
import java.io.Serializable;


/**
 * 
 * 问卷白名单表
 * 
 **/
@SuppressWarnings("serial")
public class WhiteListBO implements Serializable {

	/**用户ID**/
	private String userId;

	/**问卷ID**/
	private String questionId;

	/**录入时间**/
	private java.util.Date inputTime;

	/**有效标志**/
	private Integer validSign;



	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setInputTime(java.util.Date inputTime){
		this.inputTime = inputTime;
	}

	public java.util.Date getInputTime(){
		return this.inputTime;
	}

	public void setValidSign(Integer validSign){
		this.validSign = validSign;
	}

	public Integer getValidSign(){
		return this.validSign;
	}

}
