package com.abc12366.cms.model.questionnaire;
import java.io.Serializable;


/**
 * 
 * 答题表
 * 
 **/
@SuppressWarnings("serial")
public class Answer implements Serializable {

	/**答题记录ID**/
	private String answerLogId;

	/**题目ID**/
	private String subjectsId;

	/**答案内容**/
	private String content;



	public void setAnswerLogId(String answerLogId){
		this.answerLogId = answerLogId;
	}

	public String getAnswerLogId(){
		return this.answerLogId;
	}

	public void setSubjectId(String subjectsId){
		this.subjectsId = subjectsId;
	}

	public String getSubjectId(){
		return this.subjectsId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

}
