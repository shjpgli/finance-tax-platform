package com.abc12366.cszj.model.weixin.bo.person;

import java.io.Serializable;

public class OpenIds implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] openid;

	public String[] getOpenid() {
		return openid;
	}

	public void setOpenid(String[] openid) {
		this.openid = openid;
	}
    
}
