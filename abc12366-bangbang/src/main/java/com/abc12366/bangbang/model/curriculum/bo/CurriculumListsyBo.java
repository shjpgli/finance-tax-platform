package com.abc12366.bangbang.model.curriculum.bo;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 课程首页
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumListsyBo implements Serializable {

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

    /**课程封面**varchar(200)**/
    private String cover;

	/**发布时间**datetime**/
	private Date issueTime;

	/**课程收费**tinyint(1)**/
	private Integer isFree;

    /**课程简介**longtext**/
    private String curriculumidIntro;


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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
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
}
