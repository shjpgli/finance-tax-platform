package com.abc12366.message.model.bo;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-02
 * Time: 17:42
 */
public class QueryStatusBody {
    private String updatetime;
    private String status;
    private String mobile;

    public QueryStatusBody() {
    }

    public QueryStatusBody(String updatetime, String status, String mobile) {
        this.updatetime = updatetime;
        this.status = status;
        this.mobile = mobile;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    @Override
    public String toString() {
        return "QueryStatusBody{" +
                "updatetime=" + updatetime +
                ", status=" + status +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
