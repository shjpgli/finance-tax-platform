package com.abc12366.message.service;

import com.abc12366.message.model.bo.VerifyParam;

import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:09
 */
public interface MobileVerifyCodeService {
    void getCode(String type, String phone) throws IOException;

    void verify(VerifyParam verifyParam);


}
