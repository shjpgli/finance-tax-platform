package com.abc12366.uc.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.uc.mapper.db1.AuthorityMapper;
import com.abc12366.uc.mapper.db1.UCTokenMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.UCTokenRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.UCToken;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.UserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

import static java.util.Arrays.asList;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:07 PM
 * @since 1.0.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private AppRoMapper appRoMapper;

    @Autowired
    private UCTokenRoMapper ucTokenRoMapper;

    @Autowired
    private UCTokenMapper ucTokenMapper;

    @Transactional("db1TxManager")
    @Override
    public UserBO register(RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        User user = userRoMapper.selectByUsernameOrPhone(registerBO.getUsername());
        if(user == null){
            user = userRoMapper.selectByUsernameOrPhone(registerBO.getPhone());
        }
        if (user == null) {
            String password;
            String encodePassword = null;
            String salt = null;
            try {
                //密码生产规则：前台传密码md5之后的值，后台用该值加上salt再md5 ，salt是随机生成的六位整数
                password = Utils.md5(registerBO.getPassword());
                salt = Utils.salt();
                encodePassword = Utils.md5(password + salt);
            } catch (Exception e) {
                LOGGER.error(e.getMessage() + e);
            }
            user = new User();
            BeanUtils.copyProperties(registerBO, user);

            user.setId(Utils.uuid());
            user.setSalt(salt);
            user.setPassword(encodePassword);
            if (!StringUtils.isEmpty(registerBO.getRegMail())) {
                user.setRegMail(registerBO.getRegMail());
            }
            if (!StringUtils.isEmpty(registerBO.getUserPicturePath())) {
                user.setUserPicturePath(registerBO.getUserPicturePath());
            }
            if (!StringUtils.isEmpty(registerBO.getRegIP())) {
                user.setRegIP(registerBO.getRegIP());
            }
            if (!StringUtils.isEmpty(registerBO.getSalt())) {
                user.setSalt(registerBO.getSalt());
            }
            if (!StringUtils.isEmpty(registerBO.getRealName())) {
                user.setRealName(registerBO.getRealName());
            }
            user.setStatus(true);
            user.setCreateTime(new Date());
            user.setLastUpdate(new Date());

            int result = userMapper.insert(user);
            if (result > 0) {
                UserBO userBO1 = new UserBO();
                BeanUtils.copyProperties(user, userBO1);
                LOGGER.info("{}", userBO1);
                return userBO1;
            }
        }
        return null;
    }
    @Transactional("db1TxManager")
    @Override
    public String login(LoginBO loginBO, String appToken)  throws Exception{
        LOGGER.info("loginBO:{},appToken:{}", loginBO, appToken);
        //判断apptoken是否为空，为空则不允许登录
        if (appToken == null || appToken.equals("")) {
            return null;
        }
        User user = userRoMapper.selectByUsernameOrPhone(loginBO.getUsernameOrPhone());
        String password = null;

        //根据用户名查看用户是否存在
        if (user != null) {
            try {
                //登录密码进行处理，与表中的加密密码进行比对
                password = Utils.md5(Utils.md5(loginBO.getPassword()) + user.getSalt());
            } catch (Exception e) {
                LOGGER.error(e.getMessage() + e);
            }
            if (user.getPassword().equals(password)) {
                String userToken = Utils.token(Utils.uuid());
                user.setLastUpdate(new Date());
                int result = userMapper.update(user);
                //更新用户主表后再更新uc_token表
                if (result > 0) {
                    App appTemp = new App();
                    appTemp.setAccessToken(appToken);
                    App app = appRoMapper.selectOne(appTemp);
                    UCToken ucToken = new UCToken();
                    ucToken.setId(Utils.uuid());
                    if (app.getId() != null) {
                        ucToken.setAppId(app.getId());
                    }
                    if (user.getId() != null) {
                        ucToken.setUserId(user.getId());
                    }
                    ucToken.setToken(userToken);
                    ucToken.setLastTokenResetDate(new Date());
                    UCToken ucToken1 = ucTokenRoMapper.selectOne(user.getId(), app.getId());
                    int result02;
                    //加入uc_token表有记录（根据userId和appId），则更新，没有则新增
                    if (ucToken1 != null) {
                        result02 = ucTokenMapper.update(ucToken);
                    } else {
                        result02 = ucTokenMapper.insert(ucToken);
                    }
                    if (result02 > 0)
                        LOGGER.info("{}", userToken);
                    return userToken;
                }
            }
        }
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }
}
