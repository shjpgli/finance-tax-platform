package com.abc12366.bangbang.model.question.bo;
import com.abc12366.bangbang.model.question.QuestionTag;

import java.io.Serializable;
import java.util.List;
import java.util.Date;


/**
 * 
 * 问题表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionBo implements Serializable {

	/****varchar(64)**/
	private String id;

	/**用户ID**varchar(64)**/
	private String userId;

	/**问题**varchar(300)**/
	private String title;

	/**问题详情**longtext**/
	private String detail;

	/**问题状态**varchar(20)**/
	private String status;

	/**创建时间**datetime**/
	private java.util.Date createTime;

	/**修改时间**datetime**/
	private java.util.Date lastUpdate;

	/**悬赏积分**int(11)**/
	private Integer points;

	/**是否解决：0/1**tinyint(4)**/
	private Integer isSolve;

	/**问题分类**varchar(30)**/
	private String classifyCode;

	/**浏览量**int(11)**/
	private Integer browseNum;
    
	private java.util.Date recommendEndTime;
    private List<QuestionTag> tagList;

    /**标签**varchar(1000)**/
    private String tag;


	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return this.detail;
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

	public void setPoints(Integer points){
		this.points = points;
	}

	public Integer getPoints(){
		return this.points;
	}

	public void setIsSolve(Integer isSolve){
		this.isSolve = isSolve;
	}

	public Integer getIsSolve(){
		return this.isSolve;
	}

	public void setClassifyCode(String classifyCode){
		this.classifyCode = classifyCode;
	}

	public String getClassifyCode(){
		return this.classifyCode;
	}

	public void setBrowseNum(Integer browseNum){
		this.browseNum = browseNum;
	}

	public Integer getBrowseNum(){
		return this.browseNum;
	}

    public List<QuestionTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<QuestionTag> tagList) {
        this.tagList = tagList;
    }

	public Date getRecommendEndTime() {
		return recommendEndTime;
	}

	public QuestionBo setRecommendEndTime(Date recommendEndTime) {
		this.recommendEndTime = recommendEndTime;
		return this;
	}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
