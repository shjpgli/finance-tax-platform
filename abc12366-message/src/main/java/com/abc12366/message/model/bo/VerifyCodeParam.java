package com.abc12366.message.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-31
 * Time: 15:31
 */
public class VerifyCodeParam {
    @NotEmpty
    @Size(min = 11, max = 11)
    private String mobile;
    @NotEmpty
    @Size(min = 4, max = 10)
    private String code;

    public VerifyCodeParam() {
    }

    public VerifyCodeParam(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerifyCodeParam{" +
                "mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
