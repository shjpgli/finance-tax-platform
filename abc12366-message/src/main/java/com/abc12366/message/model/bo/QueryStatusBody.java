package com.abc12366.message.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-02
 * Time: 17:42
 */
public class QueryStatusBody {
    private Date updatetime;
    private boolean status;
    private String mobile;

    public QueryStatusBody() {
    }

    public QueryStatusBody(Date updatetime, boolean status, String mobile) {
        this.updatetime = updatetime;
        this.status = status;
        this.mobile = mobile;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "QueryStatusBody{" +
                "updatetime=" + updatetime +
                ", status=" + status +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
