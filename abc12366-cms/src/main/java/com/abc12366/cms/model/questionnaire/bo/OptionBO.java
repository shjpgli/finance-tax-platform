package com.abc12366.cms.model.questionnaire.bo;
import java.io.Serializable;


/**
 * 
 * 选项表
 * 
 **/
@SuppressWarnings("serial")
public class OptionBO implements Serializable {

	/**选项ID**/
	private String id;

	/**题目ID**/
	private String subjectsId;

	/**答案内容**/
	private String optionString;

    private Boolean status;



	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setSubjectId(String subjectsId){
		this.subjectsId = subjectsId;
	}

	public String getSubjectId(){
		return this.subjectsId;
	}

	public void setOptionString(String optionString){
		this.optionString = optionString;
	}

	public String getOptionString(){
		return this.optionString;
	}

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
