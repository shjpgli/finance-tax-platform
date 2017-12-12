package com.abc12366.uc.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-11
 * Time: 17:58
 */
public class BindStatisAllBO {
    private String type;
    private int bindCount;

    public BindStatisAllBO() {
    }

    public BindStatisAllBO(String type, int bindCount) {
        this.type = type;
        this.bindCount = bindCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBindCount() {
        return bindCount;
    }

    public void setBindCount(int bindCount) {
        this.bindCount = bindCount;
    }

    @Override
    public String toString() {
        return "BindStatisAllBO{" +
                "type='" + type + '\'' +
                ", bindCount=" + bindCount +
                '}';
    }
}
