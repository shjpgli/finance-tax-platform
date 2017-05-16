package com.abc12366.admin.service.impl;


import com.abc12366.admin.mapper.db1.LoginInfoMapper;
import com.abc12366.admin.mapper.db1.UserExtendMapper;
import com.abc12366.admin.mapper.db1.UserMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.*;
import com.abc12366.admin.model.*;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.model.bo.UserExtendBO;
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
    public List<User> selectList(User user) {
        List<User> users = userRoMapper.selectList(user);
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
    public int updateUser(UserBO userBO) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, userBO);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
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

    @Transactional("db1TxManager")
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
        return userRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public UserBO login(UserBO userBO, String appId) {
//        User user = userRoMapper.selectUserByLoginName(userBO.getUsername());
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
            //获取APP信息
            /*App appTemp = new App();
            appTemp.setAccessToken(appToken);
            appTemp.setStatus(true);
            App app = appRoMapper.selectOne(appTemp);

            if (app == null) {
                throw new ServiceException(4123);
            }*/
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
            Map<String,List<Menu>> menuMap = new HashMap<String,List<Menu>>();
            List<Role> roles = userBO.getRolesList();
            for(Role role:roles){
                List<Menu> menus = menuRoMapper.selectMenuByRoleId(role.getId());
                menuMap.put(role.getId(),menus);
            }
            userBO.setMenuMap(menuMap);
            return userBO;
        }
        return null;
    }

    @Override
    public UserExtend selectUserExtendByUserId(String id) {
        return userExtendRoMapper.selectUserExtendByUserId(id);
    }

    @Override
    public UserExtend updateUserExtend(UserExtendBO userExtendBO) {
        UserExtend userExtend = new UserExtend();
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
        }
    }


    @Override
    public boolean isAuthentication(String userToken) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(userToken);
        LoginInfo info = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if(info != null){
            return true;
        }
        return false;
    }

    @Override
    public int addUser(UserBO userBO) {
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
