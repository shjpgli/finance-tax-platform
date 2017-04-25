package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * 插件
 * 
 **/
@SuppressWarnings("serial")
public class Plug implements Serializable {

	/****/
	private String plugId;

	/**插件名称**/
	private String name;

	/**文件路径**/
	private String path;

	/**描述**/
	private String description;

	/**作者**/
	private String author;

	/**上传时间**/
	private java.util.Date uploadTime;

	/**安装时间**/
	private java.util.Date installTime;

	/**卸载时间**/
	private java.util.Date uninstallTime;

	/**包含文件是否冲突**/
	private Integer fileConflict;

	/**使用状态(0未使用,1使用中)**/
	private Integer isUsed;

	/**插件权限（,分隔各个权限配置）**/
	private String plugPerms;

	/**是否修复类插件(0 新功能插件 1修复类)**/
	private Integer plugRepair;



	public void setPlugId(String plugId){
		this.plugId = plugId;
	}

	public String getPlugId(){
		return this.plugId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return this.path;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return this.author;
	}

	public void setUploadTime(java.util.Date uploadTime){
		this.uploadTime = uploadTime;
	}

	public java.util.Date getUploadTime(){
		return this.uploadTime;
	}

	public void setInstallTime(java.util.Date installTime){
		this.installTime = installTime;
	}

	public java.util.Date getInstallTime(){
		return this.installTime;
	}

	public void setUninstallTime(java.util.Date uninstallTime){
		this.uninstallTime = uninstallTime;
	}

	public java.util.Date getUninstallTime(){
		return this.uninstallTime;
	}

	public void setFileConflict(Integer fileConflict){
		this.fileConflict = fileConflict;
	}

	public Integer getFileConflict(){
		return this.fileConflict;
	}

	public void setIsUsed(Integer isUsed){
		this.isUsed = isUsed;
	}

	public Integer getIsUsed(){
		return this.isUsed;
	}

	public void setPlugPerms(String plugPerms){
		this.plugPerms = plugPerms;
	}

	public String getPlugPerms(){
		return this.plugPerms;
	}

	public void setPlugRepair(Integer plugRepair){
		this.plugRepair = plugRepair;
	}

	public Integer getPlugRepair(){
		return this.plugRepair;
	}

}
