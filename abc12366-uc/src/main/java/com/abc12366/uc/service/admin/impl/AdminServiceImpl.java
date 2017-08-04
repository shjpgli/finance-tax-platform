package com.abc12366.uc.service.admin.impl;


import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.TimeUtil;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.AdminExtendMapper;
import com.abc12366.uc.mapper.db1.AdminMapper;
import com.abc12366.uc.mapper.db1.LoginInfoMapper;
import com.abc12366.uc.mapper.db1.UserRoleMapper;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.admin.*;
import com.abc12366.uc.model.admin.bo.*;
import com.abc12366.uc.service.admin.AdminService;
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
public class AdminServiceImpl implements AdminService {

    private static Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoMapper adminRoMapper;

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
    private AdminExtendRoMapper adminExtendRoMapper;

    @Autowired
    private AdminExtendMapper adminExtendMapper;

    @Override
    public Admin selectUserByLoginName(String username) {
        return adminRoMapper.selectUserByLoginName(username);
    }

    @Override
    public Admin selectUserById(String id) {
        return adminRoMapper.selectUserById(id);
    }

    @Override
    public List<UserBO> selectUser(Admin admin) {
        return null;
    }

    @Override
    public List<UserBO> selectList(Admin admin) {
        List<UserBO> users = adminRoMapper.selectList(admin);
        return users;
    }

    @Transactional("db1TxManager")
    @Override
    public int register(UserBO userBO) {
        Admin admin = new Admin();
        try {
            BeanUtils.copyProperties(userBO, admin);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        String password = admin.getPassword();
        admin.setId(Utils.uuid());
        try {
            admin.setPassword(Utils.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        Date date = new Date();
        admin.setCreateTime(date);
        admin.setLastUpdate(date);

        int ins = adminMapper.insert(admin);

        String id = admin.getId();
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
        adminMapper.updateUserPwdById(userId, pwd);
    }

    @Override
    public UserBO selectUserVOById(String id) {
        return adminRoMapper.selectUserBoById(id);
    }

    @Transactional("db1TxManager")
    @Override
    public UserUpdateBO updateUser(UserUpdateBO userUpdateBO) {
        UserUpdateBO uBO = new UserUpdateBO();
        Admin admin = new Admin();
        try {
            BeanUtils.copyProperties(userUpdateBO, admin);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        Date date = new Date();
        admin.setLastUpdate(date);
        //密码不为空时，给密码加密
        /*if(userUpdateBO.getPassword() != null && !"".equals(userUpdateBO.getPassword())){
            try {
                admin.setPassword(Utils.md5(userUpdateBO.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        int upd = adminMapper.updateUser(admin);
        if (upd != 1) {
            throw new ServiceException(4102);
        }

        //修改用户详情
        AdminExtend adminExtend = new AdminExtend();
        BeanUtils.copyProperties(userUpdateBO, adminExtend);
        adminExtend.setUserId(admin.getId());
        adminExtend.setLastUpdate(date);
        int updExtend = adminExtendMapper.updateUserExtentBO(adminExtend);
        if (updExtend != 1) {
            throw new ServiceException(4114);
        }
        BeanUtils.copyProperties(admin,uBO);
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
        int del = adminMapper.deleteById(id);
        adminExtendMapper.deleteByUserId(id);
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
        return adminRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public UserBO login(UserBO userBO, String appId) {
        UserBO user = adminRoMapper.selectUserBOByLoginName(userBO.getUsername());
        //判断用户是否被禁用
        if(user != null){
            Boolean status = user.getStatus();
            if(!status){
                LOGGER.error("用户为禁用状态，不能登录{}", user);
                throw new ServiceException(4126);
            }
        }
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
            long lastLong = TimeUtil.getDateStringToLong(new Date())+Constant.ADMIN_USER_TOKEN_VALID_SECONDS;
            loginInfo.setLastResetTokenTime(TimeUtil.getLongToDate(lastLong));
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
    public AdminExtend selectUserExtendByUserId(String id) {
        return adminExtendRoMapper.selectUserExtendByUserId(id);
    }


    @Override
    public AdminExtend updateUserExtend(UserExtendBO userExtendBO) {
        /*AdminExtend userExtend = new AdminExtend();
        BeanUtils.copyProperties(userExtendBO,userExtend);
        //查询该用户是否存在详情，不存在，insert；存在，Update
        AdminExtend extend = adminExtendRoMapper.selectUserExtendByUserId(userExtendBO.getUserId());
        Date date = new Date();
        if (extend == null){
            userExtend.setId(Utils.uuid());
            userExtend.setCreateTime(date);
            userExtend.setLastUpdate(date);
            int insert = adminExtendMapper.insert(userExtend);
            if(insert != 1){
                throw new ServiceException(4101);
            }
            return userExtend;
        }else{
            userExtend.setId(extend.getId());
            int upd = adminExtendMapper.updateUserExtentBO(userExtend);
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
        UserBO bo = adminRoMapper.selectUserBOByLoginName(userBO.getUsername());
        if (bo != null) {
            LOGGER.error("该用户已经存在{}:",userBO);
            throw new ServiceException(4117);
        }
        Admin admin = new Admin();
        try {
            BeanUtils.copyProperties(userBO, admin);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4105);
        }
        String password = admin.getPassword();
        admin.setId(Utils.uuid());
        try {
            admin.setPassword(Utils.md5(password));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(4106);
        }
        Date date = new Date();
        admin.setCreateTime(date);
        admin.setLastUpdate(date);

        int ins = adminMapper.insert(admin);
        if (ins != 1) {
            throw new ServiceException(4101);
        }
        AdminExtend adminExtend = new AdminExtend();
        BeanUtils.copyProperties(userBO, adminExtend);
        adminExtend.setLastUpdate(date);
        adminExtend.setCreateTime(date);
        adminExtend.setUserId(admin.getId());
        /*adminExtend.setLastUpdate(date);
        adminExtend.setCreateTime(date);
        adminExtend.setUserId(admin.getId());
        adminExtend.setAddress(userBO.getAddress());
        adminExtend.setJob(userBO.getJob());
        adminExtend.setOrgId(userBO.getOrgId());
        adminExtend.setPhone(userBO.getPhone());*/
        int extInsert = adminExtendMapper.insert(adminExtend);
        if (extInsert != 1) {
            throw new ServiceException(4112);
        }
        String id = admin.getId();
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
        BeanUtils.copyProperties(admin,temp);
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
        UserBO user = adminRoMapper.selectUserBoById(userPasswordBO.getId());
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
            update = adminMapper.updateUserPwdById(user.getId(), newPassword);
        }else{
            throw new ServiceException(4120);
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
        int update = adminMapper.updateUserPwdById(id, newPassword);
        if (update != 1) {
            throw new ServiceException(4102);
        }
        return update;
    }

    @Override
    public void enable(UserUpdateBO userUpdateBO) {
        String[] idArray = userUpdateBO.getId().split(",");
        Admin admin = new Admin();
        admin.setLastUpdate(new Date());
        for (String userId : idArray) {
            admin.setId(userId);
            admin.setStatus(userUpdateBO.getStatus());
            int update = adminMapper.updateUser(admin);
            if (update != 1) {
                LOGGER.warn("修改失败，id：{}", admin.toString());
                throw new ServiceException(4102);
            }
        }
    }

    @Override
    public Boolean checkToken(String token) {

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(token);
        //验证Token是否存在
        LoginInfo info = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if (info == null) {
            LOGGER.warn("Admin-Token不存在{}", loginInfo);
            throw new ServiceException(4128);
        }
        //严重Token是否过期
        long datelong = System.currentTimeMillis();
        long lastTime = TimeUtil.getDateStringToLong(info.getLastResetTokenTime());
        if(datelong > lastTime){
            LOGGER.warn("Admin-Token过期，请重新登录{}", info);
            throw new ServiceException(4127);
        }
        return true;
    }

    @Override
    public Boolean refreshToken(String token) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(token);
        //验证Token是否存在
        LoginInfo info = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if (info == null) {
            LOGGER.warn("Admin-Token不存在{}", info);
            throw new ServiceException(4128);
        }
        long datelong = System.currentTimeMillis()+Constant.ADMIN_USER_TOKEN_VALID_SECONDS;
        info.setLastResetTokenTime(TimeUtil.getLongToDate(datelong));
        int upd = loginInfoMapper.update(info);
        if(upd != 1){
            LOGGER.warn("修改Admin-token有效时间失败{}", info);
            throw new ServiceException(4129);
        }
        return true;
    }

    @Override
    public LoginInfoBO selectLoginInfoByToken(String token) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToken(token);
        //验证Token是否存在
        LoginInfo info = loginInfoRoMapper.selectInfoByToken(loginInfo);
        if (info == null) {
            LOGGER.warn("Admin-Token不存在{}", loginInfo);
            throw new ServiceException(4128);
        }
        //严重Token是否过期
        long dateLong = System.currentTimeMillis();
        long lastTime = TimeUtil.getDateStringToLong(info.getLastResetTokenTime());
        if(dateLong > lastTime){
            LOGGER.warn("Admin-Token过期，请重新登录{}", info);
            throw new ServiceException(4127);
        }

        long refLong = dateLong+Constant.ADMIN_USER_TOKEN_VALID_SECONDS;
        info.setLastResetTokenTime(TimeUtil.getLongToDate(refLong));
        int upd = loginInfoMapper.update(info);
        if(upd != 1){
            LOGGER.warn("修改Admin-token有效时间失败{}", info);
            throw new ServiceException(4129);
        }
        return loginInfoRoMapper.selectLoginInfoByToken(token);
    }

    @Override
    public void disableAll() {
        Admin admin = new Admin();
        List<UserBO> userBOs = adminRoMapper.selectList(admin);
        for (UserBO temp : userBOs) {
            admin.setId(temp.getId());
            admin.setStatus(false);
            int enable = adminMapper.updateUser(admin);
            if (enable != 1) {
                LOGGER.warn("修改失败，id：{}", admin.toString());
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
