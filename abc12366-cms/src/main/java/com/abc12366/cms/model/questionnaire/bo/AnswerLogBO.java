package com.abc12366.cms.model.questionnaire.bo;
import com.abc12366.cms.model.questionnaire.Answer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 答题记录表
 * 
 **/
@SuppressWarnings("serial")
public class AnswerLogBO implements Serializable {

	/****/
	private String id;

	/**问卷ID**/
	private String questionId;

	/**用户ID**/
	private String userId;

	/**接入终端**/
	private String accessTerminal;

	/**IP地址**/
	private String ipAddress;

	/**答题开始时间**/
	private java.util.Date startTime;

	/**答题结束时间**/
	private java.util.Date endTime;

    private Answer answer;

    private List<Answer> answerList = new ArrayList<Answer>();

    /**平均用时**/
    private java.util.Date avgTime;

    private String subjectsId;


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

	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}

	public java.util.Date getStartTime(){
		return this.startTime;
	}

	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	public java.util.Date getEndTime(){
		return this.endTime;
	}

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public java.util.Date getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(java.util.Date avgTime) {
        this.avgTime = avgTime;
    }

    public String getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }
}
