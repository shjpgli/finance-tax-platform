package com.abc12366.bangbang.model.question.bo;
import com.abc12366.bangbang.model.question.QuestionFactionClassify;
import com.abc12366.bangbang.model.question.QuestionFactionTag;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 邦派统计表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionFactionTjBo implements Serializable {

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

	/**入邦最低等级**varchar(64)**/
	private String minGrade;

	/**简介**varchar(1000)**/
	private String intro;

	/**是否自动通过**tinyint(4)**/
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

    /**成员数****/
    private String peopleNum;

    /**邦派荣誉值****/
    private String honor;

    /**本月成员回答数****/
    private String answerNumM;

    /**本月成员讨论数****/
    private String discussNumM;

    /**本月成员采纳回答数****/
    private String adoptNumM;

    /**本年成员回答数****/
    private String answerNumY;

    /**本年成员讨论数****/
    private String discussNumY;

    /**本年成员采纳回答数****/
    private String adoptNumY;

    /**累计成员回答数****/
    private String answerNum;

    /**累计成员讨论数****/
    private String discussNum;

    /**累计成员采纳回答数****/
    private String adoptNum;



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

	public void setMinGrade(String minGrade){
		this.minGrade = minGrade;
	}

	public String getMinGrade(){
		return this.minGrade;
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

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getAnswerNumM() {
        return answerNumM;
    }

    public void setAnswerNumM(String answerNumM) {
        this.answerNumM = answerNumM;
    }

    public String getDiscussNumM() {
        return discussNumM;
    }

    public void setDiscussNumM(String discussNumM) {
        this.discussNumM = discussNumM;
    }

    public String getAdoptNumM() {
        return adoptNumM;
    }

    public void setAdoptNumM(String adoptNumM) {
        this.adoptNumM = adoptNumM;
    }

    public String getAnswerNumY() {
        return answerNumY;
    }

    public void setAnswerNumY(String answerNumY) {
        this.answerNumY = answerNumY;
    }

    public String getDiscussNumY() {
        return discussNumY;
    }

    public void setDiscussNumY(String discussNumY) {
        this.discussNumY = discussNumY;
    }

    public String getAdoptNumY() {
        return adoptNumY;
    }

    public void setAdoptNumY(String adoptNumY) {
        this.adoptNumY = adoptNumY;
    }

    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getDiscussNum() {
        return discussNum;
    }

    public void setDiscussNum(String discussNum) {
        this.discussNum = discussNum;
    }

    public String getAdoptNum() {
        return adoptNum;
    }

    public void setAdoptNum(String adoptNum) {
        this.adoptNum = adoptNum;
    }
}
