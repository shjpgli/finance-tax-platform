package com.abc12366.bangbang.model.question;
import java.io.Serializable;


/**
 * 
 * 邦派荣誉值表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionFactionHonor implements Serializable {

	/**ID**varchar(64)**/
	private String id;

	/**邦派ID**varchar(64)**/
	private String factionId;

	/**本月荣誉值**varchar(200)**/
	private String honor;

	/**时间(年月)**int(10)**/
	private Integer honorTime;

	/**本月回答数**int(10)**/
	private Integer answerNum;

	/**本月评论数**int(10)**/
	private Integer discussNum;

	/**本月采纳数**int(10)**/
	private Integer adoptNum;

	/**本月精彩回答数**int(10)**/
	private Integer splendidNum;

	/**创建时间**datetime**/
	private java.util.Date createTime;

	/**修改时间**datetime**/
	private java.util.Date updateTime;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setFactionId(String factionId){
		this.factionId = factionId;
	}

	public String getFactionId(){
		return this.factionId;
	}

	public void setHonor(String honor){
		this.honor = honor;
	}

	public String getHonor(){
		return this.honor;
	}

	public void setHonorTime(Integer honorTime){
		this.honorTime = honorTime;
	}

	public Integer getHonorTime(){
		return this.honorTime;
	}

	public void setAnswerNum(Integer answerNum){
		this.answerNum = answerNum;
	}

	public Integer getAnswerNum(){
		return this.answerNum;
	}

	public void setDiscussNum(Integer discussNum){
		this.discussNum = discussNum;
	}

	public Integer getDiscussNum(){
		return this.discussNum;
	}

	public void setAdoptNum(Integer adoptNum){
		this.adoptNum = adoptNum;
	}

	public Integer getAdoptNum(){
		return this.adoptNum;
	}

	public void setSplendidNum(Integer splendidNum){
		this.splendidNum = splendidNum;
	}

	public Integer getSplendidNum(){
		return this.splendidNum;
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
