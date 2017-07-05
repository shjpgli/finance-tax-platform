package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.service.RealNameValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 11:07
 */
@Service
public class RealNameValidationServiceImpl implements RealNameValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealNameValidationServiceImpl.class);

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Override
    public List<UserExtendBO> selectList(String username) {
        LOGGER.info("{}", username);
        return userExtendRoMapper.selectList(username);
    }
}
