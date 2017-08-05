package com.abc12366.message.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-02
 * Time: 15:47
 */
public class SendCodeResponseBO {
    private String code;
    private String msg;
    private String obj;

    public SendCodeResponseBO() {
    }

    public SendCodeResponseBO(String code, String msg, String obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "SendCodeResponseBO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                '}';
    }
}
