package com.abc12366.cms.model.questionnaire;
import java.io.Serializable;


/**
 * 
 * 问卷表
 * 
 **/
@SuppressWarnings("serial")
public class Questionnaire implements Serializable {

	/****/
	private String id;

	/**标题**/
	private String title;

	/**简单描述**/
	private String simpleDesc;

	/**状态，true：正在回收；false：暂停回收**/
	private Boolean status;

	/**创建人**/
	private String createUser;

	/**创建时间**/
	private java.util.Date createTime;

	/**修改时间**/
	private java.util.Date updateTime;

	/**修改人**/
	private String updateUser;

	/**回收量**/
	private Integer recoveryRate;

	/**访问量**/
	private Integer accessRate;

	/**所属场景类别代码**/
	private String sceneCode;

	/**所属行业类别代码**/
	private String tradeCode;



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

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
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

}
