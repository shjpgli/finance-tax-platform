package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-26
 * Time: 16:58
 */
public class UserDzsbInsertBO {
    @NotEmpty
    private String nsrsbhOrShxydm;

    public UserDzsbInsertBO() {
    }

    public String getNsrsbhOrShxydm() {
        return nsrsbhOrShxydm;
    }

    public void setNsrsbhOrShxydm(String nsrsbhOrShxydm) {
        this.nsrsbhOrShxydm = nsrsbhOrShxydm;
    }

}
