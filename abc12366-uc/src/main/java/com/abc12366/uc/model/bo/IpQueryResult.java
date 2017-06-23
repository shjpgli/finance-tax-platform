package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.Ip;

/**
 * IP地址归属查询结果
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-22 2:30 PM
 * @since 1.0.0
 */
public class IpQueryResult {

    // 0：成功，1：失败
    private int code;

    private Ip data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Ip getData() {
        return data;
    }

    public void setData(Ip data) {
        this.data = data;
    }
}
