package com.abc12366.bangbang.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-24
 * Time: 18:26
 */
public class LetterInsertBO {
    @NotEmpty
    @Size(max = 400)
    private String content;

    public LetterInsertBO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
