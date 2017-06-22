package com.abc12366.cms.model.questionnaire.bo;
import com.abc12366.cms.model.questionnaire.QuestionnaireParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 
 * 问卷表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionnaireBO implements Serializable {

	private String id;
    @NotEmpty
    @Size(min = 2, max = 100)
	private String title;

    @Size(min = 0, max = 255)
	private String simpleDesc;

    @NotEmpty
    @Size(min = 0, max = 1)
	private String status;

	private String createUser;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private String updateUser;
	private Integer recoveryRate;
	private Integer accessRate;
	private String sceneCode;
	private String tradeCode;

    private QuestionnaireParam questionnaireParam;




	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setSimpleDesc(String simpleDesc){
		this.simpleDesc = simpleDesc;
	}

	public String getSimpleDesc(){
		return this.simpleDesc;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return this.createUser;
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

	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

	public String getUpdateUser(){
		return this.updateUser;
	}

	public void setRecoveryRate(Integer recoveryRate){
		this.recoveryRate = recoveryRate;
	}

	public Integer getRecoveryRate(){
		return this.recoveryRate;
	}

	public void setAccessRate(Integer accessRate){
		this.accessRate = accessRate;
	}

	public Integer getAccessRate(){
		return this.accessRate;
	}

	public void setSceneCode(String sceneCode){
		this.sceneCode = sceneCode;
	}

	public String getSceneCode(){
		return this.sceneCode;
	}

	public void setTradeCode(String tradeCode){
		this.tradeCode = tradeCode;
	}

	public String getTradeCode(){
		return this.tradeCode;
	}

    public QuestionnaireParam getQuestionnaireParam() {
        return questionnaireParam;
    }

    public void setQuestionnaireParam(QuestionnaireParam questionnaireParam) {
        this.questionnaireParam = questionnaireParam;
    }
}
