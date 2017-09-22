package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.mapper.db2.AppRoMapper;
import com.abc12366.gateway.model.App;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Properties;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.TokenMapper;
import com.abc12366.uc.mapper.db1.UcUserLoginLogMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.TokenRoMapper;
import com.abc12366.uc.mapper.db2.UcUserLoginLogRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.BaseObject;
import com.abc12366.uc.model.PrivilegeItem;
import com.abc12366.uc.model.Token;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.util.RandomNumber;
import com.abc12366.uc.util.UCConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:07 PM
 * @since 1.0.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static Properties properties = new Properties("application.properties");

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private UcUserLoginLogMapper loginLogMapper;

    @Autowired
    private UcUserLoginLogRoMapper loginLogRoMapper;

    @Autowired
    private ExperienceLogService experienceLogService;

    @Autowired
    private RSAService rsaService;

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private PrivilegeItemService privilegeItemService;

    /**
     * 2、新平台采用手机号码+登录密码+短信验证码注册，平台自动产生用户ID、用户名（字母UC+时间戳毫秒数）和用户昵称（财税+6位数字），同时自动绑定手机号码。
     * 3、用户ID作为平台内部字段永久有效且不可更改，平台自动产生的用户名可以允许修改一次且平台内唯一，用户名不能为中文，只能为字母+数字。
     *
     * @param registerBO
     * @return
     */
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
//            password = Utils.md5(registerBO.getPassword());
//            salt = Utils.salt();
//            encodePassword = Utils.md5(password + salt);

            //新的密码规则
            password = rsaService.decode(registerBO.getPassword());
            salt = Utils.salt();
            encodePassword = Utils.md5(password + salt);
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
            throw new ServiceException(4106);
        }
        user = new User();
        BeanUtils.copyProperties(registerBO, user);

        user.setId(Utils.uuid());
        //用户名（字母UC+时间戳毫秒数）
        user.setUsername("UC" + System.currentTimeMillis());
        //自动生成用户昵称:（财税+6位数字）
        user.setNickname("财税" + RandomNumber.getRandomNumber(6));
        user.setSalt(salt);
        user.setPassword(encodePassword);
        user.setPoints(0);
        user.setExp(0);
        user.setVipLevel(Constant.USER_ORIGINAL_LEVEL);
        user.setUsernameModifiedTimes(0);
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

        //首次绑定手机任务埋点
        todoTaskService.doTask(user.getId(), UCConstant.SYS_TASK_FIRST_PHONE_VALIDATE_ID);
        if (user.getUserPicturePath() != null && !user.getUserPicturePath().trim().equals("")) {
            //首次上传用户头像任务埋点
            todoTaskService.doTask(user.getId(), UCConstant.SYS_TASK_FIRST_UPLOAD_PICTURE_ID);
        }

        LOGGER.info("{}", userReturnBO);
        return userReturnBO;
    }

    @Transactional("db1TxManager")
    @Override
    public Map login(LoginBO loginBO, String appToken) throws Exception {
        LOGGER.info("loginBO:{},appToken:{}", loginBO, appToken);

        //根据用户名查看用户是否存在
        loginBO.setUsernameOrPhone(loginBO.getUsernameOrPhone().trim());
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4018);
        }
        //无效用户不允许登录
        if (!user.getStatus()) {
            throw new ServiceException(4038);
        }

        //登录密码进行处理，与表中的加密密码进行比对
        String password;
        try {
            //先前的加密版本
            //password = Utils.md5(Utils.md5(loginBO.getPassword()) + user.getSalt());

            //现在的加密版本
            password = rsaService.decode(loginBO.getPassword());
            LOGGER.info("password:{}", password);
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
        //假如uc_token表有记录（根据userId和appId），则更新，没有则新增
        String userToken = Utils.token(Utils.uuid());
        if (queryToken != null) {
            queryToken.setLastTokenResetTime(new Date());
            userToken = queryToken.getToken();
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

        //计算用户登录经验值变化
        computeExp(user.getId());
        //记用户登录日志
        insertLoginLog(user.getId());
        //任务日志
        todoTaskService.doTaskWithouComputeAward(user.getId(), UCConstant.SYS_TASK_LOGIN_ID);

        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        userBO.setPassword(null);

        Map<String, Object> map = new HashMap<>();
        map.put("token", userToken);
        map.put("expires_in", Constant.USER_TOKEN_VALID_SECONDS);
        map.put("user", userBO);

        // 用户信息写入redis
        valueOperations.set(userToken, JSON.toJSONString(userBO), Constant.USER_TOKEN_VALID_SECONDS / 2,
                TimeUnit.SECONDS);
        return map;
    }

    @Transactional("db1TxManager")
    @Override
    public Map loginJs(LoginBO loginBO, String appToken) throws Exception {
        LOGGER.info("loginBO:{},appToken:{}", loginBO, appToken);

        //根据用户名查看用户是否存在
        loginBO.setUsernameOrPhone(loginBO.getUsernameOrPhone().trim());
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user == null) {
            LOGGER.warn("登录失败，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4018);
        }
        //无效用户不允许登录
        if (!user.getStatus()) {
            throw new ServiceException(4038);
        }

        //登录密码进行处理，与表中的加密密码进行比对
        String password;
        try {
            //先前的加密版本
            //password = Utils.md5(Utils.md5(loginBO.getPassword()) + user.getSalt());

            //现在的加密版本
            password = rsaService.decodeStringFromJs(loginBO.getPassword());
            LOGGER.info("password:{}", password);
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
        //假如uc_token表有记录（根据userId和appId），则更新，没有则新增
        String userToken = Utils.token(Utils.uuid());
        if (queryToken != null) {
            queryToken.setLastTokenResetTime(new Date());
            userToken = queryToken.getToken();
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

        //计算用户登录经验值变化
        computeExp(user.getId());
        //记用户登录日志
        insertLoginLog(user.getId());
        //任务日志
        todoTaskService.doTaskWithouComputeAward(user.getId(), UCConstant.SYS_TASK_LOGIN_ID);

        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        userBO.setPassword(null);

        Map<String, Object> map = new HashMap<>();
        map.put("token", userToken);
        map.put("expires_in", Constant.USER_TOKEN_VALID_SECONDS);
        map.put("user", userBO);

        // 用户信息写入redis
        valueOperations.set(userToken, JSON.toJSONString(userBO), Constant.USER_TOKEN_VALID_SECONDS / 2,
                TimeUnit.SECONDS);
        return map;
    }

    private void computeExp(String userId) {
        //今日第一次登录才能获取经验值
        if (!isContinueLogin(userId, 0)) {
            //判断用户连续登录情况
            int exp = 3;
            if (isContinueLogin(userId, 1)) {
                exp = 5;
                if (isContinueLogin(userId, 2)) {
                    exp = 8;
                    if (isContinueLogin(userId, 3)) {
                        exp = 10;
                    }
                }
            }

            //会员权限埋点（经验值加成）
            PrivilegeItem privilegeItem = privilegeItemService.selecOneByUser(userId);
            if (privilegeItem != null && privilegeItem.getHyjyzjc() > 0) {
                exp = (int) (exp * privilegeItem.getHyjyzjc());
            }

            ExperienceLogBO logBO = new ExperienceLogBO();
            logBO.setId(Utils.uuid());
            logBO.setIncome(exp);
            logBO.setUserId(userId);
            logBO.setRuleId(UCConstant.EXP_RULE_LOGIN_ID);
            logBO.setOutgo(0);
            logBO.setCreateTime(new Date());
            experienceLogService.insert(logBO);
        }
    }

    private boolean isContinueLogin(String userId, int i) {
        Map<String, Object> map = new HashMap<>();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -i);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MINUTE, 0);

        calendar2.add(Calendar.DATE, -(i - 1));
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MINUTE, 0);

        map.put("userId", userId);
        map.put("startTime", calendar1.getTime());
        map.put("endTime", calendar2.getTime());
        List<UcUserLoginLog> logList = loginLogRoMapper.selectLoginLogList(map);
        if (logList != null && logList.size() > 0) {
            return true;
        }
        return false;
    }

    private void insertLoginLog(String userId) {
        UcUserLoginLog loginLog = new UcUserLoginLog();
        loginLog.setId(Utils.uuid());
        loginLog.setUserId(userId);
        loginLog.setCreateTime(new Date());
        loginLogMapper.insert(loginLog);
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
    public Map loginByVerifyingCode(VerifyingCodeBO loginBO, String appToken) throws Exception {

        //判断apptoken是否为空，为空则不允许登录
        if (appToken == null || appToken.equals("")) {
            return null;
        }
        LoginBO loginBOQery = new LoginBO();
        if (!StringUtils.isEmpty(loginBO.getPhone())) {
            loginBOQery.setUsernameOrPhone(loginBO.getPhone().trim());
        }
        User user = userRoMapper.selectByUsernameOrPhone(loginBOQery);
        if (user == null) {
            LOGGER.warn("登录失败，该用户不存在，参数:{}:{}", loginBO.toString(), appToken);
            throw new ServiceException(4018);
        }

        //无效用户不允许登录
        if (!user.getStatus()) {
            throw new ServiceException(4038);
        }

        String userToken;
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
            throw new ServiceException(4035);
        }

        Token queryToken = tokenRoMapper.selectOne(user.getId(), app.getId());
        int result02;
        //加入uc_token表有记录（根据userId和appId），则更新，没有则新增
        if (queryToken != null) {
            userToken = queryToken.getToken();
            queryToken.setLastTokenResetTime(new Date());
            result02 = tokenMapper.update(queryToken);
        } else {
            userToken = Utils.uuid();
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

        //计算用户登录经验值变化
        computeExp(user.getId());
        //记用户登录日志
        insertLoginLog(user.getId());
        //任务日志
        todoTaskService.doTaskWithouComputeAward(user.getId(), UCConstant.SYS_TASK_LOGIN_ID);

        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        return Utils.kv("token", userToken, "expires_in", Constant.USER_TOKEN_VALID_SECONDS, "user", userBO);
    }

    @Override
    public boolean verifyCode(VerifyingCodeBO loginVerifyingCodeBO, HttpServletRequest request) throws IOException {
        //不变参数
        String url = properties.getValue("chabc.soa.message.url") + "/verify";

        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", loginVerifyingCodeBO.getPhone());
        requestBody.put("code", loginVerifyingCodeBO.getCode());
        requestBody.put("type", loginVerifyingCodeBO.getType());

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }

        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            BaseObject object = JSON.parseObject(String.valueOf(responseEntity.getBody()), BaseObject.class);
            if (object.getCode().equals("2000")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout(String token) {
        LOGGER.info("{}", token);
        int result = tokenMapper.delete(token);
        // 用户信息从redis删除
        redisTemplate.delete(token);
        if (result < 1) {
            throw new ServiceException(4022);
        }
    }

    @Override
    public Map loginByopenid(UserBO user, String appToken) throws Exception {
        //更新用户主表后再更新uc_token表
        App appTemp = new App();
        appTemp.setAccessToken(appToken);
        appTemp.setStatus(true);
        App app = appRoMapper.selectOne(appTemp);
        //如果不存在有效的注册应用，则不允许登录
        if (app == null) {
            LOGGER.warn("登录失败，参数:{}:{}", user.toString(), appToken);
            throw new ServiceException(4019);
        }

        Token queryToken = tokenRoMapper.selectOne(user.getId(), app.getId());
        int result02;
        //假如uc_token表有记录（根据userId和appId），则更新，没有则新增
        String userToken = Utils.token(Utils.uuid());
        if (queryToken != null) {
            queryToken.setLastTokenResetTime(new Date());
            userToken = queryToken.getToken();
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
            LOGGER.warn("登录失败，参数:{}:{}", user.toString(), appToken);
            throw new ServiceException(4021);
        }

        //计算用户登录经验值变化
        computeExp(user.getId());
        //记用户登录日志
        insertLoginLog(user.getId());
        //任务日志
        todoTaskService.doTaskWithouComputeAward(user.getId(), UCConstant.SYS_TASK_LOGIN_ID);

        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        userBO.setPassword(null);

        Map<String, Object> map = new HashMap<>();
        map.put("token", userToken);
        map.put("expires_in", Constant.USER_TOKEN_VALID_SECONDS);
        map.put("user", userBO);

        // 用户信息写入redis
        valueOperations.set(userToken, JSON.toJSONString(userBO), Constant.USER_TOKEN_VALID_SECONDS / 2,
                TimeUnit.SECONDS);
        return map;
    }
}
