package com.abc12366.uc.model.bo;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 15:38
 */
public class NsrBindQueryParamBO {
    private String username;
    private String nsrsbh;
    private Boolean status;

    public NsrBindQueryParamBO() {
    }

    public NsrBindQueryParamBO(String username, String nsrsbh,Boolean status) {
        this.username = username;
        this.nsrsbh = nsrsbh;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
