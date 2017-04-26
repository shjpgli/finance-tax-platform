package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * 文章操作记录
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentRecord implements Serializable {

	/****/
	private String contentRecordId;

	/**文章ID**/
	private String contentId;

	/**操作人**/
	private String userId;

	/**操作时间**/
	private java.util.Date operateTime;

	/**0 新增 1修改 2审核 3退回 4移动 5生成静态页 6删除到回收站 7归档 8出档 9推送共享**/
	private Integer operateType;



	public void setContentRecordId(String contentRecordId){
		this.contentRecordId = contentRecordId;
	}

	public String getContentRecordId(){
		return this.contentRecordId;
	}

	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setOperateTime(java.util.Date operateTime){
		this.operateTime = operateTime;
	}

	public java.util.Date getOperateTime(){
		return this.operateTime;
	}

	public void setOperateType(Integer operateType){
		this.operateType = operateType;
	}

	public Integer getOperateType(){
		return this.operateType;
	}

}
