package com.abc12366.uc.model.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信统一下单返回实体类
 * 
 * @author Administrator
 *
 */
public class WxpayOrderRsp extends WxRspBase {

	private String trade_type;
	private String prepay_id;
	private String code_url;
	private String code_img;

	public Map<String, String> getReturn() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("trade_type", trade_type);
		map.put("prepay_id", prepay_id);
		map.put("code_url", code_url);
		map.put("code_img", code_img);
		return map;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	public String getCode_img() {
		return code_img;
	}

	public void setCode_img(String code_img) {
		this.code_img = code_img;
	}

}
