package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by lgy on 2017-05-08.
 */
public class NsrUpdateBO {
    private String id;
    @NotEmpty(message = "userId不能为空！")
    private String userId;
    private String djxh;
    private String nsrsbh;
    private String shxydm;
    private boolean status;

    public NsrUpdateBO() {
    }

    public NsrUpdateBO(String id, String userId, String djxh, String nsrsbh, String shxydm, boolean status) {
        this.id = id;
        this.userId = userId;
        this.djxh = djxh;
        this.nsrsbh = nsrsbh;
        this.shxydm = shxydm;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDjxh() {
        return djxh;
    }

    public void setDjxh(String djxh) {
        this.djxh = djxh;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getShxydm() {
        return shxydm;
    }

    public void setShxydm(String shxydm) {
        this.shxydm = shxydm;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NsrUpdateBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", djxh='" + djxh + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", shxydm='" + shxydm + '\'' +
                ", status=" + status +
                '}';
    }
}
