package com.abc12366.bangbang.model.question.bo;

import com.abc12366.bangbang.model.question.QuestionInvite;
import com.abc12366.bangbang.model.question.QuestionTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 问题表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionryBo implements Serializable {

	/****varchar(64)**/
	private String id;

	/**问题**varchar(300)**/
	private String title;

	/**创建时间**datetime**/
	private Date createTime;

	/**悬赏积分**int(11)**/
	private Integer points;

	/**评论次数**int(11)**/
	private Integer commentNum;

    /**用户昵称**varchar(64)**/
    private String nickname;

    /**回答ID**varchar(64)**/
    private String answerId;

    /**用户图片**/
    private String userPicturePath;

    /**简短回答内容**varchar(300)**/
    private String shortAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPicturePath() {
        return userPicturePath;
    }

    public void setUserPicturePath(String userPicturePath) {
        this.userPicturePath = userPicturePath;
    }

    public String getShortAnswer() {
        return shortAnswer;
    }

    public void setShortAnswer(String shortAnswer) {
        this.shortAnswer = shortAnswer;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
