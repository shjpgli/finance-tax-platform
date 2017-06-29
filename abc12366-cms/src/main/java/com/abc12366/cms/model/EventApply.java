package com.abc12366.cms.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * CMS活动报名申请表
 * 
 **/
@SuppressWarnings("serial")
public class EventApply implements Serializable {

	/**报名申请ID**varchar(64)**/
	private String applyId;

	/**活动ID**varchar(200)**/
	private String eventId;

	/**渠道来源**varchar(64)**/
	private String source;

	/**名称**varchar(200)**/
	private String name;

	/**电话**varchar(200)**/
	private String tel;

	/**电子邮件**varchar(200)**/
	private String email;

	/**报名时间**varchar(200)**/
	private Date applytime;

	/**报名状态**varchar(50)**/
	private String status;



	public void setApplyId(String applyId){
		this.applyId = applyId;
	}

	public String getApplyId(){
		return this.applyId;
	}

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return this.eventId;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return this.source;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setTel(String tel){
		this.tel = tel;
	}

	public String getTel(){
		return this.tel;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

}
