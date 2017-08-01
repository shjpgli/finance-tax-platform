package com.abc12366.cszj.model.weixin.bo.template;


import com.abc12366.cszj.model.weixin.BaseWxRespon;

public class ImgMaterial extends BaseWxRespon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String media_id;
    private String url;
    
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
