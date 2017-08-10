package com.abc12366.cms.model.curriculum.bo;
import java.io.Serializable;


/**
 * 
 * 课件表
 * 
 **/
@SuppressWarnings("serial")
public class CurriculumCoursewareBo implements Serializable {

	/**课件ID**varchar(64)**/
	private String coursewareId;

	/**课程ID**varchar(64)**/
	private String curriculumId;

	/**课件类型**varchar(64)**/
	private String type;

	/**课件标题**varchar(100)**/
	private String title;

	/**上传方式**tinyint(1)**/
	private Integer UploadWay;

	/**视频链接**varchar(200)**/
	private String link;

	/**文件名称**varchar(100)**/
	private String fileName;

	/**文件地址**varchar(200)**/
	private String fileSite;

	/**文件大小**varchar(10)**/
	private String fileSize;

	/**时长**varchar(10)**/
	private String duration;

	/**是否允许下载**tinyint(1)**/
	private Integer isDownload;

	/**创建时间**datetime**/
	private java.util.Date createTime;

	/**修改时间**datetime**/
	private java.util.Date updateTime;



	public void setCoursewareId(String coursewareId){
		this.coursewareId = coursewareId;
	}

	public String getCoursewareId(){
		return this.coursewareId;
	}

	public void setCurriculumId(String curriculumId){
		this.curriculumId = curriculumId;
	}

	public String getCurriculumId(){
		return this.curriculumId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setUploadWay(Integer UploadWay){
		this.UploadWay = UploadWay;
	}

	public Integer getUploadWay(){
		return this.UploadWay;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return this.link;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileSite(String fileSite){
		this.fileSite = fileSite;
	}

	public String getFileSite(){
		return this.fileSite;
	}

	public void setFileSize(String fileSize){
		this.fileSize = fileSize;
	}

	public String getFileSize(){
		return this.fileSize;
	}

	public void setDuration(String duration){
		this.duration = duration;
	}

	public String getDuration(){
		return this.duration;
	}

	public void setIsDownload(Integer isDownload){
		this.isDownload = isDownload;
	}

	public Integer getIsDownload(){
		return this.isDownload;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
