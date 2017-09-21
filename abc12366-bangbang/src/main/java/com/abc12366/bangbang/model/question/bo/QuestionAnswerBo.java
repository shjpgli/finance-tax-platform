package com.abc12366.bangbang.model.question.bo;
import java.io.Serializable;


/**
 * 
 * 问题回复表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionAnswerBo implements Serializable {

	/****varchar(64)**/
	private String id;

	/**父回答Id**varchar(64)**/
	private String parentId;

	/**问题ID**varchar(64)**/
	private String questionId;

    /**问题标题**varchar(64)**/
    private String questionTitle;

	/**用户ID**varchar(64)**/
	private String userId;

	/**回答用户ID**varchar(64)**/
	private String answerUserId;

	/**回答内容**varchar(4000)**/
	private String answer;

	/**状态**varchar(20)**/
	private String status;

	/****datetime**/
	private java.util.Date createTime;

	/****datetime**/
	private java.util.Date lastUpdate;

	/**是否采纳：1/0**tinyint(4)**/
	private Integer isAccept;

    /**ip**varchar(45)**/
    private String ip;

    /**评论次数**int(11)**/
    private Integer answerNum;

    /**点赞次数**int(11)**/
    private Integer likeNum;

    /**是否已点赞：1/0**tinyint(4)**/
    private Integer isLike;




	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
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

	public void setAnswerUserId(String answerUserId){
		this.answerUserId = answerUserId;
	}

	public String getAnswerUserId(){
		return this.answerUserId;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return this.answer;
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

	public void setIsAccept(Integer isAccept){
		this.isAccept = isAccept;
	}

	public Integer getIsAccept(){
		return this.isAccept;
	}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }
}
