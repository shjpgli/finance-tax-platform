package com.abc12366.uc.service;

import com.abc12366.uc.mapper.db1.AuthorityMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:17 AM
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public List<UserBO> selectList() {
        List<User> users = userRoMapper.selectList();

        List<UserBO> userDTOs = new ArrayList<>();
        for(User user : users) {
            UserBO userDTO = new UserBO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOs.add(userDTO);
        }
        LOGGER.info("{}", userDTOs);
        return userDTOs;
    }

    @Override
    public UserBO selectOne(Long id) {
        User user = userRoMapper.selectOne(id);

        if (user != null) {
            user.setRoles(authorityMapper.selectByUserId(user.getId()));
            UserBO userDTO = new UserBO();
            BeanUtils.copyProperties(user, userDTO);
            LOGGER.info("{}", userDTO);
            return userDTO;
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserBO update(UserUpdateBO userUpdateDTO) {
        User user = userRoMapper.selectOne(userUpdateDTO.getId());
        if (userUpdateDTO.isEnabled() != user.isEnabled()) {
            user.setEnabled(userUpdateDTO.isEnabled());
        }
        if (!StringUtils.isEmpty(userUpdateDTO.getPhone()) && !userUpdateDTO.getPhone().equals(user.getPhone())) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        user.setModifyDate(new Date());
        userMapper.update(user);

        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
        LOGGER.info("{}", userDTO);
        return userDTO;
    }

    @Override
    public UserBO selectByUsernameOrPhone(String usernameOrPhone) {
        User user = userRoMapper.selectByUsernameOrPhone(usernameOrPhone);
        if (user != null) {
            user.setRoles(authorityMapper.selectByUserId(user.getId()));
            UserBO userDTO = new UserBO();
            BeanUtils.copyProperties(user, userDTO);
            LOGGER.info("{}", userDTO);
            return userDTO;
        }
        return null;
    }
}
