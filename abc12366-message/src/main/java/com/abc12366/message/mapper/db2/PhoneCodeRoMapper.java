package com.abc12366.message.mapper.db2;

import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.bo.PhoneCodeBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 17:20
 */
public interface PhoneCodeRoMapper {
    List<PhoneCodeBO> selectList(PhoneCode phoneCode);

    List<PhoneCodeBO> selectListByPhone(String phone);
}
