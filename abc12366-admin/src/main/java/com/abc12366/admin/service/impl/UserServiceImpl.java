package com.abc12366.admin.service.impl;


import com.abc12366.admin.mapper.db1.UserMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.UserRoMapper;
import com.abc12366.admin.mapper.db2.UserRoleRoMapper;
import com.abc12366.admin.model.User;
import com.abc12366.admin.model.UserRole;
import com.abc12366.admin.service.UserService;
import com.abc12366.admin.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<UserVO> selectUser(User user) {
        return null;
    }

    @Override
    public void addUser(UserVO userVO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, userVO);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        userMapper.insert(user);

        String id = user.getId();
        String[] roles = userVO.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        for (String roleId : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updateUserPwdById(String userId, String pwd) {
        userMapper.updateUserPwdById(userId, pwd);
    }

    @Override
    public UserVO selectUserVOById(String id) {
        return userRoMapper.selectUserVoById(id);
    }

    @Override
    public void updateUser(UserVO userVO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, userVO);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        userMapper.updateUser(user);
        String id = userVO.getId();
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVO.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String roleId : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void deleteUserById(String id) {
        userMapper.deleteById(id);
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }
}
