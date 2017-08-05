package com.abc12366.uc.model.pay;

import java.io.Serializable;

import com.abc12366.uc.model.pay.bo.AliPagePayContent;
/**
 *支付宝支付请求
 * @author zhushuai 2017-8-4
 *
 */
public class AliPayReq implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AliPagePayContent payContent;
	private String notify_url;
	private String return_url;
	
	
	public AliPagePayContent getPayContent() {
		return payContent;
	}
	public void setPayContent(AliPagePayContent payContent) {
		this.payContent = payContent;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
   
   
}
