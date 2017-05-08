package com.abc12366.uc.service;

import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db1.UserExtendMapper;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
public class UserExtendServiceImpl implements UserExtendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtendService.class);

    @Autowired
    private UserExtendMapper userExtendMapper;

    private UserExtendRoMapper userExtendRoMapper;

    @Override
    public UserExtendBO selectOne(String userId) {
        LOGGER.info("{}", userId);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        UserExtendBO userExtendBO = new UserExtendBO();
        if (userExtend != null) {
            BeanUtils.copyProperties(userExtend, userExtendBO);
            LOGGER.info("{}", userExtendBO);
            return userExtendBO;
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserExtendBO insert(UserExtendBO userExtendBO) {
        if (userExtendBO == null) {
            return null;
        }
        LOGGER.info("{}", userExtendBO);
        if (!userExtendBO.getUserId().equals("")) {
            UserExtend userExtend = userExtendRoMapper.selectOne(userExtendBO.getUserId());
            if (userExtend == null) {
                userExtend = new UserExtend();
                BeanUtils.copyProperties(userExtendBO, userExtend);
                userExtend.setId(Utils.uuid());
                userExtend.setCreateTime(new Date());
                userExtend.setLastUpdate(new Date());
                int result = userExtendMapper.insert(userExtend);
                if (result > 0) {
                    UserExtendBO userExtendBO1 = new UserExtendBO();
                    BeanUtils.copyProperties(userExtend, userExtendBO1);
                    LOGGER.info("{}", userExtendBO1);
                    return userExtendBO1;
                }
            }
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserExtendBO delete(String userId) {
        LOGGER.info("{}", userId);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        if (userExtend != null) {
            int result = userExtendMapper.delete(userId);
            if (result > 0) {
                UserExtendBO userExtendBO = new UserExtendBO();
                BeanUtils.copyProperties(userExtend, userExtendBO);
                LOGGER.info("{}", userExtendBO);
                return userExtendBO;
            }
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserExtendBO update(UserExtendBO userExtendBO) {
        if (userExtendBO == null) {
            return null;
        }
        if (!userExtendBO.getUserId().equals("")) {
            UserExtend userExtend = userExtendRoMapper.selectOne(userExtendBO.getUserId());
            if (userExtend != null) {
                BeanUtils.copyProperties(userExtendBO, userExtend);
                userExtend.setLastUpdate(new Date());
                int result = userExtendMapper.update(userExtend);
                BeanUtils.copyProperties(userExtend, userExtendBO);
                if (result > 0) {
                    LOGGER.info("{}", userExtendBO);
                    return userExtendBO;
                }
            }
        }
        return null;
    }
}
