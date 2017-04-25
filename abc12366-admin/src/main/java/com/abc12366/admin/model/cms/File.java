package com.abc12366.admin.model.cms;
import java.io.Serializable;


/**
 *
 *附件表
 * add by xieyanmao on 2017-4-25
 *
 **/
@SuppressWarnings("serial")
public class File implements Serializable {

	/**文件路径**/
	private String filePath;

	/**文件名字**/
	private String fileName;

	/**是否有效**/
	private Integer fileIsvalid;

	/**内容id**/
	private String contentId;



	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	public String getFilePath(){
		return this.filePath;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileIsvalid(Integer fileIsvalid){
		this.fileIsvalid = fileIsvalid;
	}

	public Integer getFileIsvalid(){
		return this.fileIsvalid;
	}

	public void setContentId(String contentId){
		this.contentId = contentId;
	}

	public String getContentId(){
		return this.contentId;
	}

}
