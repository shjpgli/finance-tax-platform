package com.abc12366.bangbang.model.question;
import java.io.Serializable;


/**
 * 
 * 问题评论表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionComment implements Serializable {

	/****varchar(64)**/
	private String id;

	/**回答Id**varchar(64)**/
	private String answerId;

	/**问题ID**varchar(64)**/
	private String questionId;

	/**用户ID**varchar(64)**/
	private String userId;

	/**被评论用户ID**varchar(64)**/
	private String commentUserId;

	/**评论内容**varchar(4000)**/
	private String commentTxt;

	/**状态：0正常，1待审查，2拉黑**varchar(20)**/
	private String status;

	/****datetime**/
	private java.util.Date createTime;

	/****datetime**/
	private java.util.Date lastUpdate;

	/**IP**varchar(45)**/
	private String ip;

	/**邦派ID**varchar(64)**/
	private String factionId;

	/**点赞次数**int(11)**/
	private Integer likeNum;

	/**踩次数**int(11)**/
	private Integer trampleNum;

	/**被举报数**int(11)**/
	private Integer reportNum;

    /**所属分类**varchar(64)**/
    private String classifyCode;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setAnswerId(String answerId){
		this.answerId = answerId;
	}

	public String getAnswerId(){
		return this.answerId;
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

	public void setCommentUserId(String commentUserId){
		this.commentUserId = commentUserId;
	}

	public String getCommentUserId(){
		return this.commentUserId;
	}

	public void setCommentTxt(String commentTxt){
		this.commentTxt = commentTxt;
	}

	public String getCommentTxt(){
		return this.commentTxt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setLastUpdate(java.util.Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public java.util.Date getLastUpdate(){
		return this.lastUpdate;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setFactionId(String factionId){
		this.factionId = factionId;
	}

	public String getFactionId(){
		return this.factionId;
	}

	public void setLikeNum(Integer likeNum){
		this.likeNum = likeNum;
	}

	public Integer getLikeNum(){
		return this.likeNum;
	}

	public void setTrampleNum(Integer trampleNum){
		this.trampleNum = trampleNum;
	}

	public Integer getTrampleNum(){
		return this.trampleNum;
	}

	public void setReportNum(Integer reportNum){
		this.reportNum = reportNum;
	}

	public Integer getReportNum(){
		return this.reportNum;
	}

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }
}
