package com.abc12366.uc.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 前段消息发送接口类
 * @author zhushuai 2017-11-13
 *
 */
public class MessageSendBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;//用户ID
	
	private String busiType;//业务类型
	
	private String webMsg;//站内消息
	
	private String phoneMsg;//短信消息
	
	private Map<String, String> dataList;//模板消息内容
	
	private String templateid;//模板ID

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWebMsg() {
		return webMsg;
	}

	public void setWebMsg(String webMsg) {
		this.webMsg = webMsg;
	}

	public String getPhoneMsg() {
		return phoneMsg;
	}

	public void setPhoneMsg(String phoneMsg) {
		this.phoneMsg = phoneMsg;
	}

	public Map<String, String> getDataList() {
		return dataList;
	}

	public void setDataList(Map<String, String> dataList) {
		this.dataList = dataList;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	
    
}
