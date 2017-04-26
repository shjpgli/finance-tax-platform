package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS评论扩展表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class CommentExt implements Serializable {

	/****/
	private String commentId;

	/**IP地址**/
	private String ip;

	/**评论内容**/
	private String text;

	/**回复内容**/
	private String reply;



	public void setCommentId(String commentId){
		this.commentId = commentId;
	}

	public String getCommentId(){
		return this.commentId;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return this.text;
	}

	public void setReply(String reply){
		this.reply = reply;
	}

	public String getReply(){
		return this.reply;
	}

}
