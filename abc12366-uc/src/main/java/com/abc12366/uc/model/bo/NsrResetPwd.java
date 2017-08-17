package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-12
 * Time: 11:49
 */
public class NsrResetPwd {
    @NotEmpty
    private String nsrsbh;
    private String frmc;
    private String frzjh;


    public NsrResetPwd() {
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrzjh() {
        return frzjh;
    }

    public void setFrzjh(String frzjh) {
        this.frzjh = frzjh;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }
}
