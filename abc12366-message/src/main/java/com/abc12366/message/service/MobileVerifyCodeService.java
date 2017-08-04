package com.abc12366.message.service;

import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:09
 */
public interface MobileVerifyCodeService {
    void getCode(String phone) throws IOException;

    void verify(String phone, String code);
}
