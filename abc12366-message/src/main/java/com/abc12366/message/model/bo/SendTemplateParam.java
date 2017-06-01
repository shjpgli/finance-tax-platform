package com.abc12366.message.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-31
 * Time: 15:35
 */
public class SendTemplateParam {
    @NotNull
    private Integer templateid;
    @NotEmpty
    @Size(max = 2000)
    private String mobiles;
    private String params;

    public SendTemplateParam() {
    }

    public SendTemplateParam(Integer templateid, String mobiles, String params) {
        this.templateid = templateid;
        this.mobiles = mobiles;
        this.params = params;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "SendTemplateParam{" +
                "templateid=" + templateid +
                ", mobiles='" + mobiles + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
