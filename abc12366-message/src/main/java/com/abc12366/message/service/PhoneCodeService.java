package com.abc12366.message.service;

import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.bo.PhoneCodeBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 17:09
 */
public interface PhoneCodeService {
    int insert(PhoneCode phoneCode);

    List<PhoneCodeBO> selectList(PhoneCode phoneCodeParam);

    int delete(PhoneCode phoneCodeParam);
}
