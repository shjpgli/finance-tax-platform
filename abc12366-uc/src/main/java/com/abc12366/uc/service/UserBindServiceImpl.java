package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db2.UserBindRoMapper;
import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.bo.UserDzsbBO;
import com.abc12366.uc.model.bo.UserHndsBO;
import com.abc12366.uc.model.bo.UserHngsBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
@Service
public class UserBindServiceImpl implements UserBindService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindServiceImpl.class);

    @Autowired
    private UserBindMapper userBindMapper;

    @Autowired
    private UserBindRoMapper userBindRoMapper;

    @Override
    public UserDzsbBO dzsbBind(UserDzsbBO userDzsbBO) {
        if (userDzsbBO == null) {
            LOGGER.warn("新增失败，参数：{}" + userDzsbBO.toString());
            throw new ServiceException(4101);
        }
        UserDzsb userDzsb = new UserDzsb();
        BeanUtils.copyProperties(userDzsbBO, userDzsb);
        userDzsb.setId(Utils.uuid());
        Date date = new Date();
        userDzsb.setCreateTime(date);
        userDzsb.setLastUpdate(date);
        userDzsb.setStatus(true);
        int result = userBindMapper.dzsbBind(userDzsb);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4101);
        }
        UserDzsbBO userDzsbBO1 = new UserDzsbBO();
        BeanUtils.copyProperties(userDzsb, userDzsbBO1);
        return userDzsbBO1;
    }

    @Transactional("db1TxManager")
    @Override
    public boolean dzsbUnbind(String id) {
        UserDzsb userDzsb = userBindRoMapper.userDzsbSelectById(id);
        if (userDzsb == null) {
            LOGGER.warn("修改失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4102);
        }
        int result = userBindMapper.dzsbUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4102);
        }
        return true;
    }

    @Override
    public UserHngsBO hngsBind(UserHngsBO userHngsBO) {
        if (userHngsBO == null) {
            LOGGER.warn("新增失败，参数：{}" + userHngsBO.toString());
            throw new ServiceException(4101);
        }
        UserHngs userHngs = new UserHngs();
        BeanUtils.copyProperties(userHngsBO, userHngs);
        Date date = new Date();
        userHngs.setId(Utils.uuid());
        userHngs.setSmrzzt(false);
        userHngs.setStatus(true);
        userHngs.setCreateTime(date);
        userHngs.setLastUpdate(date);
        int result = userBindMapper.hngsBind(userHngs);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHngs.toString());
            throw new ServiceException(4101);
        }
        UserHngsBO userHngsBO1 = new UserHngsBO();
        BeanUtils.copyProperties(userHngs, userHngsBO1);
        return userHngsBO1;
    }

    @Transactional("db1TxManager")
    @Override
    public boolean hngsUnbind(String id) {
        UserHngs userHngs = userBindRoMapper.userHngsSelectById(id);
        if (userHngs == null) {
            LOGGER.warn("修改失败，参数：{}" + userHngs.toString());
            throw new ServiceException(4102);
        }
        int result = userBindMapper.hngsUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        return true;
    }

    @Override
    public UserHndsBO hndsBind(UserHndsBO userHndsBO) {
        if (userHndsBO == null) {
            LOGGER.warn("新增失败，参数：{}" + null);
            throw new ServiceException(4101);
        }
        UserHnds userHnds = new UserHnds();
        BeanUtils.copyProperties(userHndsBO, userHnds);
        Date date = new Date();
        userHnds.setId(Utils.uuid());
        userHnds.setStatus(true);
        userHnds.setCreateTime(date);
        userHnds.setLastUpdate(date);
        int result = userBindMapper.hndsBind(userHnds);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHnds);
            throw new ServiceException(4101);
        }
        UserHndsBO userHndsBO1 = new UserHndsBO();
        BeanUtils.copyProperties(userHnds, userHndsBO1);
        return userHndsBO1;
    }

    @Transactional("db1TxManager")
    @Override
    public boolean hndsUnbind(String id) {
        UserHnds userHnds = userBindRoMapper.userHndsSelectById(id);
        if (userHnds == null) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        int result = userBindMapper.hndsUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        return true;
    }
}
