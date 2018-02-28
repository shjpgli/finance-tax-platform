package com.abc12366.bangbang.model.question.bo;
import com.abc12366.bangbang.model.question.QuestionFactionClassify;
import com.abc12366.bangbang.model.question.QuestionFactionTag;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * 邦派表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionFactionBo implements Serializable {

	/**邦派ID**varchar(64)**/
	private String factionId;

	/**邦派名称**varchar(200)**/
	private String factionName;

	/**邦派图片**varchar(300)**/
	private String factionImg;

	/**邦派分类**varchar(500)**/
	private String classifyCode;

	/**邦派标签**varchar(500)**/
	private String tag;

    /**奖励积分**int(11)**/
    private Integer awardPoint;

	/**入邦最低等级**varchar(64)**/
	@NotNull
	private Integer minGrade;

	/**简介**varchar(1000)**/
	private String intro;

	/**是否自动通过**tinyint(4)**/
	@NotNull
	private Integer auto;

	/**邀请链接**varchar(300)**/
	private String inviteLink;

	/**创建时间**datetime**/
	private java.util.Date createTime;

	/**修改时间**datetime**/
	private java.util.Date updateTime;

	/**创建者**varchar(64)**/
	private String userId;

	/**状态**int(2)**/
	private Integer state;

	/**公告**varchar(500)**/
	private String announcement;

	/**最大人数限制**int(10)**/
	private Integer peopleLimit;

	/**邦派等级**varchar(64)**/
	private String factionGrade;

    private List<QuestionFactionTag> tagList;

    private List<QuestionFactionClassify> classifyList;



	public void setFactionId(String factionId){
		this.factionId = factionId;
	}

	public String getFactionId(){
		return this.factionId;
	}

	public void setFactionName(String factionName){
		this.factionName = factionName;
	}

	public String getFactionName(){
		return this.factionName;
	}

	public void setFactionImg(String factionImg){
		this.factionImg = factionImg;
	}

	public String getFactionImg(){
		return this.factionImg;
	}

	public void setClassifyCode(String classifyCode){
		this.classifyCode = classifyCode;
	}

	public String getClassifyCode(){
		return this.classifyCode;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return this.tag;
	}

    public Integer getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(Integer minGrade) {
        this.minGrade = minGrade;
    }

    public void setIntro(String intro){
		this.intro = intro;
	}

	public String getIntro(){
		return this.intro;
	}

	public void setAuto(Integer auto){
		this.auto = auto;
	}

	public Integer getAuto(){
		return this.auto;
	}

	public void setInviteLink(String inviteLink){
		this.inviteLink = inviteLink;
	}

	public String getInviteLink(){
		return this.inviteLink;
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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return this.state;
	}

	public void setAnnouncement(String announcement){
		this.announcement = announcement;
	}

	public String getAnnouncement(){
		return this.announcement;
	}

	public void setPeopleLimit(Integer peopleLimit){
		this.peopleLimit = peopleLimit;
	}

	public Integer getPeopleLimit(){
		return this.peopleLimit;
	}

	public void setFactionGrade(String factionGrade){
		this.factionGrade = factionGrade;
	}

	public String getFactionGrade(){
		return this.factionGrade;
	}


    public List<QuestionFactionTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<QuestionFactionTag> tagList) {
        this.tagList = tagList;
    }

    public List<QuestionFactionClassify> getClassifyList() {
        return classifyList;
    }

    public void setClassifyList(List<QuestionFactionClassify> classifyList) {
        this.classifyList = classifyList;
    }

    public Integer getAwardPoint() {
        return awardPoint;
    }

    public void setAwardPoint(Integer awardPoint) {
        this.awardPoint = awardPoint;
    }
}
