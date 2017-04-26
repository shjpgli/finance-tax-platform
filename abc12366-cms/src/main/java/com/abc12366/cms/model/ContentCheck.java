package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS内容审核信息表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentCheck implements Serializable {

	/****/
	private String contentId;

	/**审核步数**/
	private Integer checkStep;

	/**审核意见**/
	private String checkOpinion;

	/**是否退回**/
	private Integer isRejected;

	/****/
	private Integer reviewer;

	/**终审时间**/
	private java.util.Date checkDate;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setCheckStep(Integer checkStep){
		this.checkStep = checkStep;
	}

	public Integer getCheckStep(){
		return this.checkStep;
	}

	public void setCheckOpinion(String checkOpinion){
		this.checkOpinion = checkOpinion;
	}

	public String getCheckOpinion(){
		return this.checkOpinion;
	}

	public void setIsRejected(Integer isRejected){
		this.isRejected = isRejected;
	}

	public Integer getIsRejected(){
		return this.isRejected;
	}

	public void setReviewer(Integer reviewer){
		this.reviewer = reviewer;
	}

	public Integer getReviewer(){
		return this.reviewer;
	}

	public void setCheckDate(java.util.Date checkDate){
		this.checkDate = checkDate;
	}

	public java.util.Date getCheckDate(){
		return this.checkDate;
	}

}
