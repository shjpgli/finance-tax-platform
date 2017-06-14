package com.abc12366.admin.service.impl;


import com.abc12366.admin.mapper.db1.LoginInfoMapper;
import com.abc12366.admin.mapper.db1.UserExtendMapper;
import com.abc12366.admin.mapper.db1.UserMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.*;
import com.abc12366.admin.model.*;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.model.bo.UserExtendBO;
import com.abc12366.admin.model.bo.UserPasswordBO;
import com.abc12366.admin.model.bo.UserUpdateBO;
import com.abc12366.admin.service.UserService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.DateUtils;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MenuRoMapper menuRoMapper;
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Autowired
    private LoginInfoRoMapper loginInfoRoMapper;

    @Autowired
    private AppRoMapper appRoMapper;

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Autowired
    private UserExtendMapper userExtendMapper;

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
    public List<UserBO> selectList(User user) {
        List<UserBO> users = userRoMapper.selectList(user);
        return users;
    }

    @Transactional("db1TxManager")
    @Override
    public int register(UserBO userBO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(userBO, user);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        String password = user.getPassword();
        user.setId(Utils.uuid());
        try {
            user.setPassword(Utils.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setLastUpdate(date);

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
        return userRoMapper.selectUserBoById(id);
    }

    @Transactional("db1TxManager")
    @Override
    public UserUpdateBO updateUser(UserUpdateBO userUpdateBO) {
        UserUpdateBO uBO = new UserUpdateBO();
        User user = new User();
        try {
            BeanUtils.copyProperties(userUpdateBO, user);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        Date date = new Date();
        user.setLastUpdate(date);
        //密码不为空时，给密码加密
        /*if(userUpdateBO.getPassword() != null && !"".equals(userUpdateBO.getPassword())){
            try {
                user.setPassword(Utils.md5(userUpdateBO.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        int upd = userMapper.updateUser(user);
        if (upd != 1) {
            throw new ServiceException(4102);
        }

        //修改用户详情
        UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(userUpdateBO, userExtend);
        userExtend.setUserId(user.getId());
        userExtend.setLastUpdate(date);
        int updExtend = userExtendMapper.updateUserExtentBO(userExtend);
        if (updExtend != 1) {
            throw new ServiceException(4114);
        }
        BeanUtils.copyProperties(user,uBO);
        String id = userUpdateBO.getId();
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userUpdateBO.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String roleId : roles) {
            userRole.setId(Utils.uuid());
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
        return uBO;
    }

    @Transactional("db1TxManager")
    @Override
    public int deleteUserById(String id) {
        int del = userMapper.deleteById(id);
        userExtendMapper.deleteByUserId(id);
        loginInfoMapper.deleteByUserId(id);
        List<UserRole> userRoles = userRoleRoMapper.selectUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
        return del;
    }

    @Override
    public UserBO selectOne(String id) {
        return userRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public UserBO login(UserBO userBO, String appId) {
        UserBO user = userRoMapper.selectUserBOByLoginName(userBO.getUsername());
        String password = "";
        try {
            password = Utils.md5(userBO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        if (user != null && password.equals(user.getPassword())) {
            Date now = new Date();
            String userToken = null;
            try {
                userToken = Utils.md5(Utils.uuid());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException(4106);
            }
            //查找用户登录信息
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserId(user.getId());
            loginInfo.setAppId(appId);
            Date date = new Date();
            loginInfo.setLastResetTokenTime(DateUtils.addHours(date, Constant.USER_TOKEN_VALID_HOURS));
            LoginInfo info = loginInfoRoMapper.selectOne(loginInfo);
            //判断该用户是否存在此应用的登录信息
            loginInfo.setToken(userToken);
            if (info != null) {
                loginInfo.setId(info.getId());
                int update = loginInfoMapper.update(loginInfo);
                if (update != 1) {
                    LOGGER.error("修改登录信息失败：{}", update);
                    throw new ServiceException(4132);
                }
            } else {
                loginInfo.setId(Utils.uuid());
                int insert = loginInfoMapper.insertSelective(loginInfo);
                if (insert != 1) {
                    LOGGER.error("新增登录信息失败：{}", insert);
                    throw new ServiceException(4131);
                }
            }
            BeanUtils.copyProperties(user, userBO);
            userBO.setLoginInfo(loginInfo);

            //查询用户菜单信息
            Map<String, List<Menu>> menuMap = new HashMap<String, List<Menu>>();
            List<Role> roles = userBO.getRolesList();
            for (Role role : roles) {
                List<Menu> menus = menuRoMapper.selectMenuByRoleId(role.getId());
                menuMap.put(role.getId(), menus);
            }
            userBO.setMenuMap(menuMap);
            return userBO;
        } else {
            throw new ServiceException(4125);
        }
    }

    @Override
    public UserExtend selectUserExtendByUserId(String id) {
        return userExtendRoMapper.selectUserExtendByUserId(id);
    }


    @Override
    public UserExtend updateUserExtend(UserExtendBO userExtendBO) {
        /*UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(userExtendBO,userExtend);
        //查询该用户是否存在详情，不存在，insert；存在，Update
        UserExtend extend = userExtendRoMapper.selectUserExtendByUserId(userExtendBO.getUserId());
        Date date = new Date();
        if (extend == null){
            userExtend.setId(Utils.uuid());
            userExtend.setCreateTime(date);
            userExtend.setLastUpdate(date);
            int insert = userExtendMapper.insert(userExtend);
            if(insert != 1){
                throw new ServiceException(4101);
            }
            return userExtend;
        }else{
            userExtend.setId(extend.getId());
            int upd = userExtendMapper.updateUserExtentBO(userExtend);
            if(upd != 1){
                throw new ServiceException(4102);
            }
            return userExtend;
        }*/
        return null;
    }


    @Override
    public boolean isAuthentication(String userToken) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(userToken);
        LoginInfo info = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if (info != null) {
            return true;
        }
        return false;
    }

    @Transactional("db1TxManager")
    @Override
    public UserBO addUser(UserBO userBO) {
        UserBO bo = userRoMapper.selectUserBOByLoginName(userBO.getUsername());
        if (bo != null) {
            LOGGER.error("该用户已经存在");
            throw new ServiceException(4117);
        }
        User user = new User();
        try {
            BeanUtils.copyProperties(userBO, user);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        String password = user.getPassword();
        user.setId(Utils.uuid());
        try {
            user.setPassword(Utils.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setLastUpdate(date);

        int ins = userMapper.insert(user);
        if (ins != 1) {
            throw new ServiceException(4101);
        }
        UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(userBO, userExtend);
        userExtend.setLastUpdate(date);
        userExtend.setCreateTime(date);
        userExtend.setUserId(user.getId());
        /*userExtend.setLastUpdate(date);
        userExtend.setCreateTime(date);
        userExtend.setUserId(user.getId());
        userExtend.setAddress(userBO.getAddress());
        userExtend.setJob(userBO.getJob());
        userExtend.setOrgId(userBO.getOrgId());
        userExtend.setPhone(userBO.getPhone());*/
        int extInsert = userExtendMapper.insert(userExtend);
        if (extInsert != 1) {
            throw new ServiceException(4112);
        }
        String id = user.getId();
        String[] roles = userBO.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        int roleIns = 0;
        for (String roleId : roles) {
            userRole.setId(Utils.uuid());
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            roleIns = userRoleMapper.insert(userRole);
            if (roleIns != 1) {
                throw new ServiceException(4113);
            }
        }
        UserBO temp = new UserBO();
        BeanUtils.copyProperties(user,temp);
        return temp;
    }

    @Override
    public void logout(String token) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(token);
        //退出时，重置token
        LoginInfo temp = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if (temp != null) {
            try {
                String newToken = Utils.md5(Utils.uuid());
                temp.setToken(newToken);
                loginInfoMapper.update(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int updateUserPwd(UserPasswordBO userPasswordBO) {

        int update = 0;
        UserBO user = userRoMapper.selectUserBoById(userPasswordBO.getId());
        String newPassword;
        String oldPassword;
        try {
            newPassword = Utils.md5(userPasswordBO.getNewPassword());
            oldPassword = Utils.md5(userPasswordBO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        if (user != null && oldPassword.equals(user.getPassword())) {
            update = userMapper.updateUserPwdById(user.getId(), newPassword);
        }

        return update;
    }

    @Override
    public int resetUserPwd(String id) {
        String newPassword;
        try {
            newPassword = Utils.md5(Constant.defaultPwd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        int update = userMapper.updateUserPwdById(id, newPassword);
        if (update != 1) {
            throw new ServiceException(4102);
        }
        return update;
    }

    @Override
    public void enable(UserUpdateBO userUpdateBO) {
        String[] idArray = userUpdateBO.getId().split(",");
        User user = new User();
        user.setLastUpdate(new Date());
        for (String userId : idArray) {
            user.setId(userId);
            user.setStatus(userUpdateBO.getStatus());
            int update = userMapper.updateUser(user);
            if (update != 1) {
                LOGGER.warn("修改失败，id：{}", user.toString());
                throw new ServiceException(4102);
            }
        }
    }

    @Override
    public void disableAll() {
        User user = new User();
        List<UserBO> userBOs = userRoMapper.selectList(user);
        for (UserBO temp : userBOs) {
            user.setId(temp.getId());
            user.setStatus(false);
            int enable = userMapper.updateUser(user);
            if (enable != 1) {
                LOGGER.warn("修改失败，id：{}", user.toString());
                throw new ServiceException(4102);
            }
        }
    }

    private LoginInfo getLoginInfo(UserBO user, String userToken, App app) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(user.getId());
        loginInfo.setAppId(app.getId());
        loginInfo.setToken(userToken);
        Date date = new Date();
        loginInfo.setLastResetTokenTime(DateUtils.addHours(date, Constant.USER_TOKEN_VALID_HOURS));
        LoginInfo info = loginInfoRoMapper.selectOne(loginInfo);
        //判断该用户是否存在此应用的登录信息
        if (info != null) {
            loginInfo.setId(info.getId());
            int update = loginInfoMapper.update(loginInfo);
            if (update != 1) {
                LOGGER.error("修改登录信息失败：{}", update);
                throw new ServiceException(4132);
            }
        } else {
            loginInfo.setId(Utils.uuid());
            int insert = loginInfoMapper.insertSelective(loginInfo);
            if (insert != 1) {
                LOGGER.error("新增登录信息失败：{}", insert);
                throw new ServiceException(4131);
            }
        }
        return loginInfo;
    }
}
