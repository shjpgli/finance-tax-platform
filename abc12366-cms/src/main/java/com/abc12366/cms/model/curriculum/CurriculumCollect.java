package com.abc12366.cms.model.curriculum;
import java.io.Serializable;


/**
 * 
 * 课程收藏表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumCollect implements Serializable {

	/**收藏ID**varchar(64)**/
	private String collectId;

	/**课程ID**varchar(64)**/
	private String curriculumId;

	/**用户ID**varchar(64)**/
	private String userId;



	public void setCollectId(String collectId){
		this.collectId = collectId;
	}

	public String getCollectId(){
		return this.collectId;
	}

	public void setCurriculumId(String curriculumId){
		this.curriculumId = curriculumId;
	}

	public String getCurriculumId(){
		return this.curriculumId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

}
