package com.abc12366.cms.model;
import java.io.Serializable;


/**
 * 
 * CMS活动记录表
 * 
 **/
@SuppressWarnings("serial")
public class EventRecord implements Serializable {

	/**活动记录ID**varchar(64)**/
	private String recordId;

	/**活动ID**varchar(64)**/
	private String eventId;

	/**渠道来源**varchar(30)**/
	private String source;

	/**浏览时间**datetime**/
	private java.util.Date browsetime;

	/**访问IP**varchar(50)**/
	private String ip;



	public void setRecordId(String recordId){
		this.recordId = recordId;
	}

	public String getRecordId(){
		return this.recordId;
	}

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return this.eventId;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return this.source;
	}

	public void setBrowsetime(java.util.Date browsetime){
		this.browsetime = browsetime;
	}

	public java.util.Date getBrowsetime(){
		return this.browsetime;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return this.ip;
	}

}
