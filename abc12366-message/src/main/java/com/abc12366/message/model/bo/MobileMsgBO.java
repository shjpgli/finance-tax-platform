package com.abc12366.message.model.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-10
 * Time: 16:10
 */
public class MobileMsgBO {
    @NotNull(message = "验证码类型type不能为空")
    private String content;
    @NotNull(message = "手机号码phone不能为空")
    @Pattern(regexp = "^\\d{11}$")
    @Size(min = 11, max = 11)
    private String phone;

    public MobileMsgBO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "MobileMsgBO{" +
                "content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
