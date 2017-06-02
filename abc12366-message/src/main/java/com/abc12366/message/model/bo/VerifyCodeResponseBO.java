package com.abc12366.message.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-02
 * Time: 15:58
 */
public class VerifyCodeResponseBO {
    private String code;

    public VerifyCodeResponseBO() {
    }

    public VerifyCodeResponseBO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerifyCodeResponseBO{" +
                "code='" + code + '\'' +
                '}';
    }
}
