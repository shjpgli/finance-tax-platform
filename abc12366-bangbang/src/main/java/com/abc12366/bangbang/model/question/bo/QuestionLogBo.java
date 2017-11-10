package com.abc12366.bangbang.model.question.bo;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 问题动态记录表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionLogBo implements Serializable {

	/**帮帮动态记录ID**varchar(64)**/
	private String id;

	/**用户ID**varchar(64)**/
	private String userId;

	/**被关注用户ID**varchar(64)**/
	private String attentionUserId;

    /**问题或者秘籍ID**int**/
    private String qcId;

    /**回复ID**int**/
    private String answerId;

	/**来源ID**datetime**/
	private String sourceId;

    /**日志类型**varchar(64)**/
    private Integer qlogType;

    /**创建时间**datetime**/
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttentionUserId() {
        return attentionUserId;
    }

    public void setAttentionUserId(String attentionUserId) {
        this.attentionUserId = attentionUserId;
    }

    public String getQcId() {
        return qcId;
    }

    public void setQcId(String qcId) {
        this.qcId = qcId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getQlogType() {
        return qlogType;
    }

    public void setQlogType(Integer qlogType) {
        this.qlogType = qlogType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
