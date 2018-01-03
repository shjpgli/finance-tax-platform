package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db1.UserBsrlMapper;
import com.abc12366.uc.mapper.db2.UserBsrlRoMapper;
import com.abc12366.uc.model.UserBsrl;
import com.abc12366.uc.model.bo.UserBindBO;
import com.abc12366.uc.model.bo.UserBsrlBO;
import com.abc12366.uc.service.UserBsrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/2.
 */
@Service("userBsrlService")
public class UserBsrlServiceImpl implements UserBsrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBsrlServiceImpl.class);

    @Autowired
    private UserBsrlMapper userBsrlMapper;

    @Autowired
    private UserBsrlRoMapper userBsrlRoMapper;

    @Override
    public List<UserBsrl> selectList(Map<String, Object> map) {
        return userBsrlRoMapper.selectList(map);
    }

    @Override
    public UserBindBO selectById(String id) {
        return userBsrlRoMapper.selectById(id);
    }

    @Override
    public UserBsrlBO insert(UserBsrlBO userBsrlBO) {
        userBsrlBO.setCalId(Utils.uuid());
        userBsrlBO.setXgsj(DateUtils.dateToString(new Date()));
        UserBsrl userBsrl = new UserBsrl();
        BeanUtils.copyProperties(userBsrlBO,userBsrl);

        int insert = userBsrlMapper.insert(userBsrl);
        if (insert != 1) {
            LOGGER.info("新增异常：{}", insert);
            throw new ServiceException(4101);
        }
        return userBsrlBO;
    }

    @Override
    public UserBsrlBO update(UserBsrlBO userBsrlBO) {
        userBsrlBO.setXgsj(DateUtils.dateToString(new Date()));
        UserBsrl userBsrl = new UserBsrl();
        BeanUtils.copyProperties(userBsrlBO,userBsrl);
        int update = userBsrlMapper.update(userBsrl);
        if (update != 1) {
            LOGGER.info("修改异常：{}", update);
            throw new ServiceException(4102);
        }
        return userBsrlBO;
    }

    @Override
    public void delete(String id) {
        int del = userBsrlMapper.delete(id);
        if (del != 1) {
            LOGGER.info("删除异常：{}", del);
            throw new ServiceException(4103);
        }
    }
}
