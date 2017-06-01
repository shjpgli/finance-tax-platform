package com.abc12366.message.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-05-27
 * Time: 17:36
 */
public class SendCodeParam {
    @NotEmpty
    @Size(min = 11, max = 11)
    private String mobile;
    private String deviceId;
    private Integer templateid;
    @Min(4)
    @Max(10)
    private Integer codeLen;

    public SendCodeParam() {
    }

    public SendCodeParam(String mobile, String deviceId, Integer templateid, Integer codeLen) {
        this.mobile = mobile;
        this.deviceId = deviceId;
        this.templateid = templateid;
        this.codeLen = codeLen;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public Integer getCodeLen() {
        return codeLen;
    }

    public void setCodeLen(Integer codeLen) {
        this.codeLen = codeLen;
    }

    @Override
    public String toString() {
        return "SendCodeParam{" +
                "mobile='" + mobile + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", templateid=" + templateid +
                ", codeLen=" + codeLen +
                '}';
    }
}
