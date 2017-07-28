package com.abc12366.cszj.model.weixin.bo;


import com.abc12366.cszj.model.weixin.BaseWxRespon;
/**
 * wx UseToken
 * @author zhushuai 2017-7-27
 *
 */
public class WxUseToken extends BaseWxRespon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String access_token;
	private Integer expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

}
