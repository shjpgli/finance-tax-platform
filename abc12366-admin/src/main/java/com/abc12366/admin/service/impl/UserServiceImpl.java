package com.abc12366.admin.service.impl;


import com.abc12366.admin.mapper.db1.UserMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.UserRoMapper;
import com.abc12366.admin.mapper.db2.UserRoleRoMapper;
import com.abc12366.admin.model.Role;
import com.abc12366.admin.model.User;
import com.abc12366.admin.model.UserRole;
import com.abc12366.admin.service.UserService;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.bo.TokenBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleRoMapper userRoleRoMapper;

    @Override
    public User selectUserByLoginName(String username) {
        return userRoMapper.selectUserByLoginName(username);
    }

    @Override
    public User selectUserById(String id) {
        return userRoMapper.selectUserById(id);
    }

    @Override
    public List<UserBO> selectUser(User user) {
        return null;
    }

    @Override
    public List<User> selectList() {
        List<User> users = userRoMapper.selectList();
        /*if(users != null){
            List<UserBO> userBOs = new ArrayList<UserBO>();
            for (User user:users){
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(user,userBO);
                userBOs.add(userBO);
            }
            LOGGER.info("{}", userBOs);
            return userBOs;
        }*/
        return users;
    }

    @Override
    public int register(UserBO userBO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userBO, user);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        String password = user.getPassword();
        user.setId(Utils.uuid());
        try {
            user.setPassword(Utils.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setLastUpdate(date);
        TokenBO tokenBO = new TokenBO.Builder().id(user.getId())
                .name(user.getUsername())
                .createTime(date)
                .expireTime(Utils.addHours(date, Constant.APP_TOKEN_VALID_HOURS))
                .build();

        String token = Utils.token(tokenBO);
        int ins = userMapper.insert(user);

        String id = user.getId();
        String[] roles = userBO.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        for (String roleId : roles) {
            userRole.setId(Utils.uuid());
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
        return ins;
    }

    @Override
    public void updateUserPwdById(String userId, String pwd) {
        userMapper.updateUserPwdById(userId, pwd);
    }

    @Override
    public UserBO selectUserVOById(String id) {
        return userRoMapper.selectUserVoById(id);
    }

    @Override
    public int updateUser(UserBO userBO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, userBO);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        user.setLastUpdate(new Date());
        int upd = userMapper.updateUser(user);

        String id = userBO.getId();
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userBO.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String roleId : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
        return upd;
    }

    @Override
    public int deleteUserById(String id) {
        int del = userMapper.deleteById(id);
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
        return del;
    }

    @Override
    public User selectOne(String id) {
//        User user = new User();
//        BeanUtils.copyProperties(userBO,user);
        return userRoMapper.selectOne(id);
    }

    @Override
    public UserBO login(UserBO userBO) {
        return null;
    }
}
