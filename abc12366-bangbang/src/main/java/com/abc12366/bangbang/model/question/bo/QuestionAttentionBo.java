package com.abc12366.bangbang.model.question.bo;
import java.io.Serializable;


/**
 * 
 * 帮邦关注表
 * 
 **/
@SuppressWarnings("serial")
public class QuestionAttentionBo implements Serializable {

	/**关注ID**varchar(64)**/
	private String attentionId;

	/**被关注用户ID**varchar(64)**/
	private String attentionUserId;

	/**用户ID**varchar(64)**/
	private String userId;

	/**关注时间**datetime**/
	private java.util.Date attentionTime;

    /**用户昵称**/
    private String nickname;

    /**用户图片地址**/
    private String userPicturePath;

    /**提问数**/
    private Integer questionNum;

    /**回复数**/
    private Integer answerNum;

    /**粉丝数**/
    private Integer attentionNum;

    /**是否关注我**/
    private Integer isAttention;



	public void setAttentionId(String attentionId){
		this.attentionId = attentionId;
	}

	public String getAttentionId(){
		return this.attentionId;
	}

	public void setAttentionUserId(String attentionUserId){
		this.attentionUserId = attentionUserId;
	}

	public String getAttentionUserId(){
		return this.attentionUserId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setAttentionTime(java.util.Date attentionTime){
		this.attentionTime = attentionTime;
	}

	public java.util.Date getAttentionTime(){
		return this.attentionTime;
	}

}
