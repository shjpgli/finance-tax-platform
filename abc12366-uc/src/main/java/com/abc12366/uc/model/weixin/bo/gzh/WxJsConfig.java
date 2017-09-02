package com.abc12366.uc.model.weixin.bo.gzh;

import java.io.Serializable;

public class WxJsConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;
    private String url;
    private String charge;
    
    public WxJsConfig(){
    	
    }
    
    public WxJsConfig(String appId,String timestamp,String nonceStr,String signature,String url,String charge){
    	this.appId=appId;
    	this.timestamp=timestamp;
    	this.nonceStr=nonceStr;
    	this.signature=signature;
    	this.url=url;
    	this.charge=charge;
    }
    
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}
    
    
}
