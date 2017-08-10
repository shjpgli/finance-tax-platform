package com.abc12366.cms.model.curriculum.bo;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 课程表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumListBo implements Serializable {

	/**课程ID**varchar(64)**/
	private String curriculumId;

    /**课程标题**varchar(200)**/
    private String title;

	/**课程分类**varchar(64)**/
	private String classify;

	/**授课方式**varchar(64)**/
	private String teachingMethod;

	/**发布时间**datetime**/
	private java.util.Date issueTime;

	/**课程收费**tinyint(1)**/
	private Integer isFree;

	/**课程状态**tinyint(1)**/
	private Integer status;

	/**创建人名称**varchar(30)**/
	private String createrName;

	/**修改时间**datetime**/
	private java.util.Date updateTime;

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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getTeachingMethod() {
        return teachingMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        this.teachingMethod = teachingMethod;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
