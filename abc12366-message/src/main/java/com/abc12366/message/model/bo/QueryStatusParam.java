package com.abc12366.message.model.bo;

import javax.validation.constraints.NotNull;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-31
 * Time: 15:39
 */
public class QueryStatusParam {
    @NotNull
    private Long sendid;

    public QueryStatusParam() {
    }

    public QueryStatusParam(Long sendid) {
        this.sendid = sendid;
    }

    public Long getSendid() {
        return sendid;
    }

    public void setSendid(Long sendid) {
        this.sendid = sendid;
    }

    @Override
    public String toString() {
        return "QueryStatusParam{" +
                "sendid=" + sendid +
                '}';
    }
}
