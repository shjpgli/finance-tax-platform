package com.abc12366.cms.model;
import java.io.Serializable;


/**
 *
 * CMS内容附件表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class ContentAttachment implements Serializable {

	/****/
	private String contentId;

	/**排列顺序**/
	private Integer priority;

	/**附件路径**/
	private String attachmentPath;

	/**附件名称**/
	private String attachmentName;

	/**文件名**/
	private String filename;

	/**下载次数**/
	private Integer downloadCount;



	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

	public void setAttachmentPath(String attachmentPath){
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentPath(){
		return this.attachmentPath;
	}

	public void setAttachmentName(String attachmentName){
		this.attachmentName = attachmentName;
	}

	public String getAttachmentName(){
		return this.attachmentName;
	}

	public void setFilename(String filename){
		this.filename = filename;
	}

	public String getFilename(){
		return this.filename;
	}

	public void setDownloadCount(Integer downloadCount){
		this.downloadCount = downloadCount;
	}

	public Integer getDownloadCount(){
		return this.downloadCount;
	}

}
