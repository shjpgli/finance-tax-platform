package com.abc12366.message.model.bo;

import java.util.Date;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 1:26
 */
public class NeteaseQueryStatusObj {
    private String status;
    private String mobile;
    private Date updatetime;

    public NeteaseQueryStatusObj() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "NeteaseQueryStatusObj{" +
                "status='" + status + '\'' +
                ", mobile='" + mobile + '\'' +
                ", updatetime=" + updatetime +
                '}';
    }
}
