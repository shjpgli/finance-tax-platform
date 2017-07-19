package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Properties;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.uc.mapper.db1.TokenMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.TokenRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.Token;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:07 PM
 * @since 1.0.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private AppRoMapper appRoMapper;

    @Autowired
    private TokenRoMapper tokenRoMapper;

    @Autowired
    private TokenMapper tokenMapper;

    private static Properties properties = new Properties("application.properties");

    @Transactional("db1TxManager")
    @Override
    public UserReturnBO register(RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        if (registerBO == null) {
            LOGGER.warn("新增失败，参数:{}" + null);
            throw new ServiceException(4101);
        }

        LoginBO loginBO = new LoginBO();
        if (!StringUtils.isEmpty(registerBO.getPhone())) {
            loginBO.setUsernameOrPhone(registerBO.getPhone());
        }
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user != null) {
            LOGGER.warn("新增失败，参数:{}" + registerBO.toString());
            throw new ServiceException(4117);
        }
        String password;
        String encodePassword;
        String salt;
        try {
            //密码生产规则：前台传密码md5之后的值，后台用该值加上salt再md5 ，salt是随机生成的六位整数
            password = Utils.md5(registerBO.getPassword());
            salt = Utils.salt();
            encodePassword = Utils.md5(password + salt);
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
            throw new ServiceException(4106);
        }
        user = new User();
        BeanUtils.copyProperties(registerBO, user);

        user.setId(Utils.uuid());
        user.setUsername("username" + registerBO.getPhone());
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

        user.setStatus(true);
        user.setCreateTime(new Date());
        user.setLastUpdate(new Date());

        int result = userMapper.insert(user);
        if (result != 1) {
            LOGGER.warn("新增失败，参数:{}" + registerBO.toString());
            throw new ServiceException(4020);
        }
        UserReturnBO userReturnBO = new UserReturnBO();
        BeanUtils.copyProperties(user, userReturnBO);
        LOGGER.info("{}", userReturnBO);
        return userReturnBO;
    }

    @Transactional("db1TxManager")
    @Override
    public Map login(LoginBO loginBO, String appToken) throws Exception {
        LOGGER.info("loginBO:{},appToken:{}", loginBO, appToken);

        //根据用户名查看用户是否存在
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4018);
        }
        String password;
        try {
            //登录密码进行处理，与表中的加密密码进行比对
            password = Utils.md5(Utils.md5(loginBO.getPassword()) + user.getSalt());
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
            throw new ServiceException(4106);
        }
        if (!user.getPassword().equals(password)) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4120);
        }

        user.setLastUpdate(new Date());
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4102);
        }
        //更新用户主表后再更新uc_token表
        App appTemp = new App();
        appTemp.setAccessToken(appToken);
        appTemp.setStatus(true);
        App app = appRoMapper.selectOne(appTemp);
        //如果不存在有效的注册应用，则不允许登录
        if (app == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4019);
        }

        Token queryToken = tokenRoMapper.selectOne(user.getId(), app.getId());
        int result02;
        //加入uc_token表有记录（根据userId和appId），则更新，没有则新增
        String userToken = Utils.token(Utils.uuid());
        if (queryToken != null) {
            queryToken.setLastTokenResetTime(new Date());
            result02 = tokenMapper.update(queryToken);
        } else {
            Token token = new Token();
            token.setId(Utils.uuid());
            if (app.getId() != null) {
                token.setAppId(app.getId());
            }
            if (user.getId() != null) {
                token.setUserId(user.getId());
            }
            token.setToken(userToken);
            token.setLastTokenResetTime(new Date());
            result02 = tokenMapper.insert(token);
        }
        if (result02 != 1) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4021);
        }
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);

        Map map = new HashMap<>();
        map.put("User-Token", userToken);
        map.put("expires_in", Constant.USER_TOKEN_VALID_SECONDS);
        map.put("user", userBO);
        return map;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }

    @Override
    public boolean isAuthentication(String userToken, HttpServletRequest request) {
        Token token = tokenRoMapper.isAuthentication(userToken);
        if (token == null) {
            throw new ServiceException(4016);
        }
        long lastTokenResetTime = token.getLastTokenResetTime().getTime();
        long currentTime = new Date().getTime();
        if (currentTime > (lastTokenResetTime + 1000 * Constant.USER_TOKEN_VALID_SECONDS)) {
            throw new ServiceException(4015);
        }
        //把用户Id设置到request
        if (!StringUtils.isEmpty(request.getAttribute(Constant.USER_ID))) {
            request.removeAttribute(Constant.USER_ID);
            request.setAttribute(Constant.USER_ID, token.getUserId());
        } else {
            request.setAttribute(Constant.USER_ID, token.getUserId());
        }
        //刷新token
        refreshToken(userToken);
        return true;
    }

    @Override
    public boolean refreshToken(String oldToken) {
        if (StringUtils.isEmpty(oldToken)) {
            return false;
        }
        Token token = tokenRoMapper.isAuthentication(oldToken);
        if (token == null) {
            return false;
        }
        token.setLastTokenResetTime(new Date());
        int result = tokenMapper.update(token);
        if (result < 1) {
            throw new ServiceException(4017);
        }
        return true;
    }

    @Override
    public Map loginByVerifyingCode(LoginVerifyingCodeBO loginBO, String appToken) throws Exception {

        //判断apptoken是否为空，为空则不允许登录
        if (appToken == null || appToken.equals("")) {
            return null;
        }
        LoginBO loginBOQery = new LoginBO();
        if (!StringUtils.isEmpty(loginBO.getPhone())) {
            loginBOQery.setUsernameOrPhone(loginBO.getPhone());
        }
        User user = userRoMapper.selectByUsernameOrPhone(loginBOQery);
        if (user == null) {
            LOGGER.warn("登录失败，该用户不存在，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4104);
        }

        String userToken = Utils.token(Utils.uuid());
        user.setLastUpdate(new Date());
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4102);
        }
        //更新用户主表后再更新uc_token表
        App appTemp = new App();
        appTemp.setAccessToken(appToken);
        appTemp.setStatus(true);
        App app = appRoMapper.selectOne(appTemp);
        //如果不存在有效的注册应用，则不允许登录
        if (app == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4104);
        }

        Token queryToken = tokenRoMapper.selectOne(user.getId(), app.getId());
        int result02;
        //加入uc_token表有记录（根据userId和appId），则更新，没有则新增
        if (queryToken != null) {
            queryToken.setLastTokenResetTime(new Date());
            result02 = tokenMapper.update(queryToken);
        } else {
            Token token = new Token();
            token.setId(Utils.uuid());
            if (app.getId() != null) {
                token.setAppId(app.getId());
            }
            if (user.getId() != null) {
                token.setUserId(user.getId());
            }
            token.setToken(userToken);
            token.setLastTokenResetTime(new Date());
            result02 = tokenMapper.insert(token);
        }
        if (result02 != 1) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4101);
        }
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        return Utils.kv("User-Token", userToken, "expires_in", Constant.USER_TOKEN_VALID_SECONDS, "user", userBO);
    }

    @Override
    public ResponseEntity verifyCode(String phone, String code, HttpServletRequest request) throws IOException {
        //不变参数
        //String url = "http://localhost:9200/message/sms/verifycode";
        String url = properties.getValue("message.message.url.verifycode");

        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile", phone);
        requestBody.put("code", code);

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);

        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            if (responseEntity.hasBody()) {
                return responseEntity;
            } else {
                throw new ServiceException(4201);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void logout(String token) {
        LOGGER.info("{}", token);
        int result = tokenMapper.delete(token);
        if (result < 1) {
            throw new ServiceException(4022);
        }
    }
}
