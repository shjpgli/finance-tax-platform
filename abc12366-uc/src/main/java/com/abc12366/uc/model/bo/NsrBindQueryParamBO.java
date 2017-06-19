package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 15:38
 */
public class NsrBindQueryParamBO {
    private String username;
    private String nsrsbh;

    public NsrBindQueryParamBO() {
    }

    public NsrBindQueryParamBO(String username, String nsrsbh) {
        this.username = username;
        this.nsrsbh = nsrsbh;
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
}
