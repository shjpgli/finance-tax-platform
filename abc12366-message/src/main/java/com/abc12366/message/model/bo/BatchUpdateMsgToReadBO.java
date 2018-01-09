package com.abc12366.message.model.bo;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2018-01-09
 * Time: 17:06
 */
public class BatchUpdateMsgToReadBO {
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "BatchUpdateMsgToReadBO{" +
                "ids=" + ids +
                '}';
    }
}
