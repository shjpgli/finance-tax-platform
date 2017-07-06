package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.uc.mapper.db1.UserExtendMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendListBO;
import com.abc12366.uc.service.RealNameValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserExtendMapper userExtendMapper;

    @Override
    public List<UserExtendListBO> selectList(Map map) {
        LOGGER.info("{}", map);
        return userExtendRoMapper.selectList(map);
    }

    @Override
    public UserExtendBO validate(String userId, String validStatus) throws ParseException {
        LOGGER.info("{}:{}", userId, validStatus);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        if (userExtend == null) {
            throw new ServiceException(4701);
        }
        UserExtend userExtendUpdate = new UserExtend();
        Date startTime = new Date();

        userExtendUpdate.setUserId(userId);
        userExtendUpdate.setLastUpdate(startTime);
        userExtendUpdate.setStartTime(startTime);
        userExtendUpdate.setEndTime(getSpecifiedDate("2099-12-30 23:59:59"));
        userExtendUpdate.setValidStatus(validStatus);
        int result = userExtendMapper.update(userExtendUpdate);
        if (result < 1) {
            throw new ServiceException();
        }
        UserExtendBO userExtendBO = new UserExtendBO();
        BeanUtils.copyProperties(userExtend, userExtendBO);
        userExtendBO.setValidStatus(userExtendUpdate.getValidStatus());
        return userExtendBO;
    }

    public Date getSpecifiedDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
