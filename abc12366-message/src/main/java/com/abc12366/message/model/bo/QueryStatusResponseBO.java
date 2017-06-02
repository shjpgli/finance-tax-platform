package com.abc12366.message.model.bo;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-02
 * Time: 16:06
 */
public class QueryStatusResponseBO {
    private String code;
    private List<QueryStatusBody> obj;

    public QueryStatusResponseBO() {
    }

    public QueryStatusResponseBO(String code, List obj) {
        this.code = code;
        this.obj = obj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getObj() {
        return obj;
    }

    public void setObj(List obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "QueryStatusResponseBO{" +
                "code='" + code + '\'' +
                ", obj=" + obj +
                '}';
    }
}
