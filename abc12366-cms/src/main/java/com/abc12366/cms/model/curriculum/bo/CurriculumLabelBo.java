package com.abc12366.cms.model.curriculum.bo;
import java.io.Serializable;


/**
 * 
 * 课程标签表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumLabelBo implements Serializable {

	/**课程ID**varchar(64)**/
	private String curriculumId;

	/**标签ID**varchar(64)**/
	private String labelId;



	public void setCurriculumId(String curriculumId){
		this.curriculumId = curriculumId;
	}

	public String getCurriculumId(){
		return this.curriculumId;
	}

	public void setLabelId(String labelId){
		this.labelId = labelId;
	}

	public String getLabelId(){
		return this.labelId;
	}

}
