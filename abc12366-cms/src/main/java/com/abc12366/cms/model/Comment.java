package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS评论表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class Comment implements Serializable {

	/****/
	private String commentId;

	/**评论用户ID**/
	private Integer commentUserId;

	/**回复用户ID**/
	private Integer replyUserId;

	/**内容ID**/
	private String contentId;

	/**站点ID**/
	private String siteId;

	/**评论时间**/
	private java.util.Date createTime;

	/**回复时间**/
	private java.util.Date replyTime;

	/**支持数**/
	private Integer ups;

	/**反对数**/
	private Integer downs;

	/**是否推荐**/
	private Integer isRecommend;

	/**是否审核**/
	private Integer isChecked;

	/**评分**/
	private Integer score;

	/**父级评论**/
	private Integer parentId;

	/**回复数**/
	private Integer replyCount;



	public void setCommentId(String commentId){
		this.commentId = commentId;
	}

	public String getCommentId(){
		return this.commentId;
	}

	public void setCommentUserId(Integer commentUserId){
		this.commentUserId = commentUserId;
	}

	public Integer getCommentUserId(){
		return this.commentUserId;
	}

	public void setReplyUserId(Integer replyUserId){
		this.replyUserId = replyUserId;
	}

	public Integer getReplyUserId(){
		return this.replyUserId;
	}

	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setReplyTime(java.util.Date replyTime){
		this.replyTime = replyTime;
	}

	public java.util.Date getReplyTime(){
		return this.replyTime;
	}

	public void setUps(Integer ups){
		this.ups = ups;
	}

	public Integer getUps(){
		return this.ups;
	}

	public void setDowns(Integer downs){
		this.downs = downs;
	}

	public Integer getDowns(){
		return this.downs;
	}

	public void setIsRecommend(Integer isRecommend){
		this.isRecommend = isRecommend;
	}

	public Integer getIsRecommend(){
		return this.isRecommend;
	}

	public void setIsChecked(Integer isChecked){
		this.isChecked = isChecked;
	}

	public Integer getIsChecked(){
		return this.isChecked;
	}

	public void setScore(Integer score){
		this.score = score;
	}

	public Integer getScore(){
		return this.score;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
	}

	public void setReplyCount(Integer replyCount){
		this.replyCount = replyCount;
	}

	public Integer getReplyCount(){
		return this.replyCount;
	}

}
