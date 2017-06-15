package com.abc12366.cms.model.questionnaire;
import java.io.Serializable;


/**
 * 
 * 抽奖设置表
 * 
 **/
@SuppressWarnings("serial")
public class PrizeSet implements Serializable {

	/**问卷ID**/
	private String questionId;

	/**截止时间**/
	private java.util.Date endTime;

	/**发奖人**/
	private String awardUser;

	/**联系方式**/
	private String contactWay;

	/**发奖方式**/
	private String awardWay;

	/**中奖概率**/
	private double prizeRate;



	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	public java.util.Date getEndTime(){
		return this.endTime;
	}

	public void setAwardUser(String awardUser){
		this.awardUser = awardUser;
	}

	public String getAwardUser(){
		return this.awardUser;
	}

	public void setContactWay(String contactWay){
		this.contactWay = contactWay;
	}

	public String getContactWay(){
		return this.contactWay;
	}

	public void setAwardWay(String awardWay){
		this.awardWay = awardWay;
	}

	public String getAwardWay(){
		return this.awardWay;
	}

	public void setPrizeRate(double prizeRate){
		this.prizeRate = prizeRate;
	}

	public double getPrizeRate(){
		return this.prizeRate;
	}

}
