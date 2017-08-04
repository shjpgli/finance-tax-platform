package com.abc12366.cms.model.questionnaire.bo;

import com.abc12366.cms.model.questionnaire.Option;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 题目表
 * 
 **/
@SuppressWarnings("serial")
public class SubjectsBO implements Serializable {

	private String id;
    @NotEmpty
	private String questionId;
	private String title;
	private String simpleDesc;
	private Integer isRequired;
	private String optionType;
	private String isQuestion;
	private Integer pages;
	private String picPath;
	private String picName;
	private Integer randomOrder;
	private Integer displayMultiple;
	private Integer mostOptional;
    @NotNull
	private Integer number;

    private List<Option> optionList = new ArrayList<Option>();



	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return this.questionId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setSimpleDesc(String simpleDesc){
		this.simpleDesc = simpleDesc;
	}

	public String getSimpleDesc(){
		return this.simpleDesc;
	}

	public void setIsRequired(Integer isRequired){
		this.isRequired = isRequired;
	}

	public Integer getIsRequired(){
		return this.isRequired;
	}

	public void setOptionType(String optionType){
		this.optionType = optionType;
	}

	public String getOptionType(){
		return this.optionType;
	}

	public void setIsQuestion(String isQuestion){
		this.isQuestion = isQuestion;
	}

	public String getIsQuestion(){
		return this.isQuestion;
	}

	public void setPages(Integer pages){
		this.pages = pages;
	}

	public Integer getPages(){
		return this.pages;
	}

	public void setPicPath(String picPath){
		this.picPath = picPath;
	}

	public String getPicPath(){
		return this.picPath;
	}

	public void setPicName(String picName){
		this.picName = picName;
	}

	public String getPicName(){
		return this.picName;
	}

	public void setRandomOrder(Integer randomOrder){
		this.randomOrder = randomOrder;
	}

	public Integer getRandomOrder(){
		return this.randomOrder;
	}

	public void setDisplayMultiple(Integer displayMultiple){
		this.displayMultiple = displayMultiple;
	}

	public Integer getDisplayMultiple(){
		return this.displayMultiple;
	}

	public void setMostOptional(Integer mostOptional){
		this.mostOptional = mostOptional;
	}

	public Integer getMostOptional(){
		return this.mostOptional;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
