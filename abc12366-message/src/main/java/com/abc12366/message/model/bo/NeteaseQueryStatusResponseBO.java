package com.abc12366.message.model.bo;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 1:27
 */
public class NeteaseQueryStatusResponseBO {
    private String code;
    private List<NeteaseQueryStatusObj> obj;

    public NeteaseQueryStatusResponseBO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NeteaseQueryStatusObj> getObj() {
        return obj;
    }

    public void setObj(List<NeteaseQueryStatusObj> obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "NeteaseQueryStatusResponseBO{" +
                "code='" + code + '\'' +
                ", obj=" + obj +
                '}';
    }
}
