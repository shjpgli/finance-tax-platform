package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS友情链接类别
 * 
 **/
@SuppressWarnings("serial")
public class FriendlinkCtg implements Serializable {

	/****/
	private String friendlinkctgId;

	/****/
	private String siteId;

	/**名称**/
	private String friendlinkctgName;

	/**排列顺序**/
	private Integer priority;



	public void setFriendlinkctgId(String friendlinkctgId){
		this.friendlinkctgId = friendlinkctgId;
	}

	public String getFriendlinkctgId(){
		return this.friendlinkctgId;
	}

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}

	public String getSiteId(){
		return this.siteId;
	}

	public void setFriendlinkctgName(String friendlinkctgName){
		this.friendlinkctgName = friendlinkctgName;
	}

	public String getFriendlinkctgName(){
		return this.friendlinkctgName;
	}

	public void setPriority(Integer priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return this.priority;
	}

}
