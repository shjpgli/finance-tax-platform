package com.abc12366.bangbang.model.curriculum.bo;
import com.abc12366.bangbang.model.curriculum.CurriculumLabel;
import com.abc12366.bangbang.model.curriculum.CurriculumLecturerGx;
import com.abc12366.bangbang.model.curriculum.CurriculumMembergrade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 课程表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumsyBo implements Serializable {

	/**课程ID**varchar(64)**/
	private String curriculumId;

    /**课程标题**varchar(200)**/
    private String title;

    /**浏览量**tinyint(11)**/
    private Integer browseNum;

    /**观看数**tinyint(11)**/
    private Integer watchNum;

    /**商品ID**varchar(64)**/
    private String goodsId;

	/**发布时间**datetime**/
	private java.util.Date issueTime;

	/**课程封面**varchar(200)**/
	private String cover;

	/**课程收费**tinyint(1)**/
	private Integer isFree;

	/**课程简介**longtext**/
	private String curriculumidIntro;

    /**好评数**int**/
    private Integer evaluateNum45;

    /**中等评数**int**/
    private Integer evaluateNum23;

    /**差评论数**int**/
    private Integer evaluateNum1;

    /**总评论数**int**/
    private Integer evaluateNum;

    /**是否收藏**int**/
    private Integer iscollect;

    //标签
    private List<CurriculumLabel> labelList;

    //会员等级
    private List<CurriculumMembergrade> membergradeList;

    //讲师
    private List<CurriculumLecturerBo> lecturerList;

    //章节
    private List<CurriculumChapterBo> chapterBoList;

    //相关课程
    List<CurriculumListsyBo> curriculumListBoList;

    public String getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(String curriculumId) {
        this.curriculumId = curriculumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(Integer watchNum) {
        this.watchNum = watchNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getCurriculumidIntro() {
        return curriculumidIntro;
    }

    public void setCurriculumidIntro(String curriculumidIntro) {
        this.curriculumidIntro = curriculumidIntro;
    }

    public Integer getEvaluateNum45() {
        return evaluateNum45;
    }

    public void setEvaluateNum45(Integer evaluateNum45) {
        this.evaluateNum45 = evaluateNum45;
    }

    public Integer getEvaluateNum23() {
        return evaluateNum23;
    }

    public void setEvaluateNum23(Integer evaluateNum23) {
        this.evaluateNum23 = evaluateNum23;
    }

    public Integer getEvaluateNum1() {
        return evaluateNum1;
    }

    public void setEvaluateNum1(Integer evaluateNum1) {
        this.evaluateNum1 = evaluateNum1;
    }

    public Integer getEvaluateNum() {
        return evaluateNum;
    }

    public void setEvaluateNum(Integer evaluateNum) {
        this.evaluateNum = evaluateNum;
    }

    public Integer getIscollect() {
        return iscollect;
    }

    public void setIscollect(Integer iscollect) {
        this.iscollect = iscollect;
    }

    public List<CurriculumLabel> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<CurriculumLabel> labelList) {
        this.labelList = labelList;
    }

    public List<CurriculumMembergrade> getMembergradeList() {
        return membergradeList;
    }

    public void setMembergradeList(List<CurriculumMembergrade> membergradeList) {
        this.membergradeList = membergradeList;
    }

    public List<CurriculumLecturerBo> getLecturerList() {
        return lecturerList;
    }

    public void setLecturerList(List<CurriculumLecturerBo> lecturerList) {
        this.lecturerList = lecturerList;
    }

    public List<CurriculumChapterBo> getChapterBoList() {
        return chapterBoList;
    }

    public void setChapterBoList(List<CurriculumChapterBo> chapterBoList) {
        this.chapterBoList = chapterBoList;
    }

    public List<CurriculumListsyBo> getCurriculumListBoList() {
        return curriculumListBoList;
    }

    public void setCurriculumListBoList(List<CurriculumListsyBo> curriculumListBoList) {
        this.curriculumListBoList = curriculumListBoList;
    }
}
