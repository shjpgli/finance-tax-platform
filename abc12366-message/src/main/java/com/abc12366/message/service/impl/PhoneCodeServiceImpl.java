package com.abc12366.message.service.impl;

import com.abc12366.message.mapper.db1.PhoneCodeMapper;
import com.abc12366.message.mapper.db2.PhoneCodeRoMapper;
import com.abc12366.message.model.PhoneCode;
import com.abc12366.message.model.bo.PhoneCodeBO;
import com.abc12366.message.service.PhoneCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 17:09
 */
@Service
public class PhoneCodeServiceImpl implements PhoneCodeService {

    @Autowired
    private PhoneCodeMapper phoneCodeMapper;

    @Autowired
    private PhoneCodeRoMapper phoneCodeRoMapper;

    @Override
    public int insert(PhoneCode phoneCode) {
        int result = phoneCodeMapper.insert(phoneCode);
        if (result < 1) {

        }
        return result;
    }

    @Override
    public List<PhoneCodeBO> selectList(PhoneCode phoneCodeParam) {
        return phoneCodeRoMapper.selectList(phoneCodeParam);
    }

    @Override
    public int delete(PhoneCode phoneCodeParam) {
        return phoneCodeMapper.delete(phoneCodeParam);
    }
}
