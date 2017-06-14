package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
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

    @Transactional("db1TxManager")
    @Override
    public UserReturnBO register(RegisterBO registerBO) {
        LOGGER.info("{}", registerBO);
        if (registerBO == null) {
            LOGGER.warn("新增失败，参数:{}" + null);
            throw new ServiceException(4101);
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", "username" + registerBO.getPhone());
        if (!StringUtils.isEmpty(registerBO.getPhone())) {
            map.put("phone", registerBO.getPhone());
        }
        User user = userRoMapper.selectByUsernameOrPhone(map);
        if (user != null) {
            LOGGER.warn("新增失败，参数:{}" + registerBO.toString());
            throw new ServiceException(4101);
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
            throw new ServiceException(4101);
        }
        user = new User();
        BeanUtils.copyProperties(registerBO, user);

        user.setId(Utils.uuid());
        user.setUsername(map.get("username"));
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
            throw new ServiceException(4101);
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
        //判断apptoken是否为空，为空则不允许登录
        if (appToken == null || appToken.equals("")) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(loginBO.getUsernameOrPhone())) {
            map.put("username", loginBO.getUsernameOrPhone());
            map.put("phone", loginBO.getUsernameOrPhone());
        }
        User user = userRoMapper.selectByUsernameOrPhone(map);
        if (user == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4104);
        }
        String password;
        //根据用户名查看用户是否存在
        try {
            //登录密码进行处理，与表中的加密密码进行比对
            password = Utils.md5(Utils.md5(loginBO.getPassword()) + user.getSalt());
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
            throw new ServiceException(4104);
        }
        if (!user.getPassword().equals(password)) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
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
    public String refresh(String oldToken) {
        return null;
    }

    @Override
    public boolean isAuthentication(String userToken) {
        Token token = tokenRoMapper.isAuthentication(userToken);
        if (token == null) {
            return false;
        }
        long lastTokenResetTime = token.getLastTokenResetTime().getTime();
        long currentTime = new Date().getTime();
        if (currentTime > (lastTokenResetTime + 1000 * Constant.USER_TOKEN_VALID_SECONDS)) {
            throw new ServiceException(4015);
        }
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
        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Map loginByVerifyingCode(LoginVerifyingCodeBO loginBO, String appToken) throws Exception {

        //判断apptoken是否为空，为空则不允许登录
        if (appToken == null || appToken.equals("")) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(loginBO.getPhone())) {
            map.put("phone", loginBO.getPhone());
        }
        User user = userRoMapper.selectByUsernameOrPhone(map);
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
        //String url = properties.getValue("message.netease.url.verifycode");
        //不变参数
        //String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        //String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        //String contentType = properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        //String charset = properties.getValue("message.netease.charset");//"utf-8";
        //可变参数
        //String nonce = Utils.uuid();
        //String curTime = String.valueOf((new Date()).getTime() / 1000L);
        //请求头设置
        //HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("appKey", appKey);
        //httpHeaders.add("appSecret", appSecret);
        //httpHeaders.add("Content-Type", "application/json");
        //httpHeaders.add(Constant.VERSION_HEAD, Constant.VERSION_1);
        //httpHeaders.add("Nonce", nonce);
        //httpHeaders.add("CurTime", curTime);

        //MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //requestBody.add("mobile", phone);
        //requestBody.add("code", code);

        //String reqJsonStr = "{\"mobile\":"+ phone +",\"code\":" + code + "}";

        //HttpEntity requestEntity = new HttpEntity(reqJsonStr, httpHeaders);

        //ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


        //------------------------------------------
        //不变参数
        //String url = properties.getValue("message.netease.url.verifycode");
        String url = "http://localhost:9200/message/sms/verifycode";
        //String appKey = properties.getValue("message.netease.appKey");//"2dea65aed55012fd8e4686177392412e";
        //String appSecret = properties.getValue("message.netease.appSecret");//"cf03fe4b439f";
        String contentType = "application/json";//properties.getValue("message.netease.contentType");//"application/x-www-form-urlencoded";
        //String charset = "utf-8";//properties.getValue("message.netease.charset");//"utf-8";
        //可变参数
        //String nonce = Utils.uuid();
        //String curTime = String.valueOf((new Date()).getTime() / 1000L);
        //String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("appKey", appKey);
        //httpHeaders.add("appSecret", appSecret);
//        httpHeaders.add("Content-Type", contentType);
        //httpHeaders.add("charset", charset);
        //httpHeaders.add("Nonce", nonce);
        //httpHeaders.add("CurTime", curTime);
        //httpHeaders.add("CheckSum", checkSum);
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
}
