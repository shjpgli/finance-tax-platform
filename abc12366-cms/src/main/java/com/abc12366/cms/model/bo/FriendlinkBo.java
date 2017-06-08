package com.abc12366.cms.model.bo;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * CMS友情链接
 * 
 **/
@SuppressWarnings("serial")
public class FriendlinkBo implements Serializable {

	/**friendlinkId**varchar(64)**/
	private String friendlinkId;

	/**siteId**varchar(64)**/
	private String siteId;

	/**friendlinkctgId**varchar(64)**/
	private String friendlinkctgId;

	/**网站名称**varchar(150)**/
	private String siteName;

	/**网站地址**varchar(255)**/
	private String domain;

	/**友情链接logo文件名**varchar(150)**/
	private String logo;

	/**友情链接logo图片**站点Logo**/
	List<Byte> content;

	/**站长邮箱**varchar(100)**/
	private String email;

	/**网站简介**varchar(255)**/
	private String description;

	/**点击次数**int(11)**/
	private Integer views;

	/**是否显示**char(1)**/
	private String isEnabled;

	/**排列顺序**int(11)**/
	private Integer priority;



	public void setFriendlinkId(String friendlinkId){
		this.friendlinkId = friendlinkId;
	}

	public String getFriendlinkId(){
		return this.friendlinkId;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setFriendlinkctgId(String friendlinkctgId){
		this.friendlinkctgId = friendlinkctgId;
	}

	public String getFriendlinkctgId(){
		return this.friendlinkctgId;
	}

	public void setSiteName(String siteName){
		this.siteName = siteName;
	}

	public String getSiteName(){
		return this.siteName;
	}

	public void setDomain(String domain){
		this.domain = domain;
	}

	public String getDomain(){
		return this.domain;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return this.logo;
	}

	public List<Byte> getContent() {
		return content;
	}

	public void setContent(List<Byte> content) {
		this.content = content;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setViews(Integer views){
		this.views = views;
	}

	public Integer getViews(){
		return this.views;
	}

	public void setIsEnabled(String isEnabled){
		this.isEnabled = isEnabled;
	}

	public String getIsEnabled(){
		return this.isEnabled;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

}
