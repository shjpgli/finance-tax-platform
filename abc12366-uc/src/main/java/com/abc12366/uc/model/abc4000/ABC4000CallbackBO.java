package com.abc12366.uc.model.abc4000;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-10
 * Time: 10:34
 */
public class ABC4000CallbackBO {
    @NotEmpty
    private String userid;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dlsj;
    private List<NSRXXBO> t_nsrxx;

    public ABC4000CallbackBO() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Timestamp getDlsj() {
        return dlsj;
    }

    public void setDlsj(Timestamp dlsj) {
        this.dlsj = dlsj;
    }

    public List<NSRXXBO> getT_nsrxx() {
        return t_nsrxx;
    }

    public void setT_nsrxx(List<NSRXXBO> t_nsrxx) {
        this.t_nsrxx = t_nsrxx;
    }
}
