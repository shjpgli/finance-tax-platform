package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.TaskConstant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.TokenMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.TokenRoMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.BaseObject;
import com.abc12366.uc.model.Token;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:17 AM
 * @since 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private TokenRoMapper tokenRoMapper;

    @Autowired
    private RSAService rsaService;

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    /**
     * 会员日志服务
     */
    @Autowired
    private VipLogService vipLogService;

    @Autowired
    private UserFeedbackMsgService userFeedbackMsgService;

    @Override
    public List<UserBO> selectList(Map<String, Object> map) {
        //解析多标签名称参数
        List tagNameList = new ArrayList<>();
        if (map.get("tagName") != null && !map.get("tagName").equals("")) {
            tagNameList = analysisTagName((String) map.get("tagName"), ",");
        }
        map.put("tagName", tagNameList);
        map.put("tagNameCount", (tagNameList == null) ? 0 : tagNameList.size());
        List<UserBO> users = userRoMapper.selectList(map);
        if (users.size() < 1) {
            return null;
        }
        LOGGER.info("{}", users);
        return users;
    }

    @Override
    public User selectUser(String userId) {
        User userTemp = userRoMapper.selectOne(userId);
        return userTemp;
    }

    @Override
    public Map selectOne(String userId) {
        LOGGER.info("{}", userId);
        User userTemp = userRoMapper.selectOne(userId);
        UserExtend user_extend = userExtendRoMapper.selectOne(userId);
        if (userTemp != null) {
            UserBO user = new UserBO();
            BeanUtils.copyProperties(userTemp, user);
            //用户重要信息模糊化处理:电话号码
            if (!StringUtils.isEmpty(user.getPhone()) && user.getPhone().length() >= 8) {
                String phone = user.getPhone();
                StringBuilder phoneFuffer = new StringBuilder(phone);
                user.setPhone(phoneFuffer.replace(3, phone.length() - 4, "****").toString());
            }
            user.setPassword(null);
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("user_extend", user_extend);
            LOGGER.info("{}", map);
            return map;
        }
        return null;
    }

    @Override
    public void enableOrDisable(String id, String status) {
        LOGGER.info("{}:{}", id, status);
        if ((!status.equals("true")) && (!status.equals("false"))) {
            throw new ServiceException(4614);
        }
        boolean modifyStatus = status.equals("true");
        User obj = new User();
        obj.setId(id);
        obj.setStatus(modifyStatus);
        obj.setLastUpdate(new Date());
        int result = userMapper.update(obj);
        if (result < 1) {
            if (modifyStatus) {
                throw new ServiceException(4623);
            }
            throw new ServiceException(4624);
        }
    }

    @Override
    public UserBO update(UserUpdateBO userUpdateBO) {
        LOGGER.info("{}", userUpdateBO);
        //用户名转成小写
        if (!StringUtils.isEmpty(userUpdateBO.getUsername())) {
            userUpdateBO.setUsername(userUpdateBO.getUsername().trim().toLowerCase());
        }

        User user = userRoMapper.selectOne(userUpdateBO.getId());
        if (user == null) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4018);
        }

        //普通用户只允许修改一次用户名
        if (!StringUtils.isEmpty(userUpdateBO.getUsername()) && !userUpdateBO.getUsername().trim().equals(user.getUsername()) &&
                user.getUsernameModifiedTimes() >= 1) {
            throw new ServiceException(4037);
        }

        //进行用户名唯一性确认
        if (userUpdateBO.getUsername() != null) {
            LoginBO loginBO = new LoginBO();
            loginBO.setUsernameOrPhone(userUpdateBO.getUsername());
            User userOnly = userRoMapper.selectByUsernameOrPhone(loginBO);
            if (userOnly != null && !userOnly.getId().equals(userUpdateBO.getId())) {
                throw new ServiceException(4182);
            }
        }
        if (userUpdateBO.getUsername() != null && !user.getUsername().equals(userUpdateBO.getUsername())) {
            user.setUsernameModifiedTimes(user.getUsernameModifiedTimes() + 1);
        }
        BeanUtils.copyProperties(userUpdateBO, user);
        user.setLastUpdate(new Date());
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
        }

        if (user.getUserPicturePath() != null && !user.getUserPicturePath().trim().equals("")) {
            //首次上传用户头像任务埋点
            todoTaskService.doTask(user.getId(), TaskConstant.SYS_TASK_FIRST_UPLOAD_PICTURE_CODE);
        }

        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setPassword(null);

        //用户重要信息模糊化处理:电话号码
        if (!StringUtils.isEmpty(userDTO.getPhone()) && userDTO.getPhone().length() >= 8) {
            String phone = userDTO.getPhone();
            StringBuilder phoneFuffer = new StringBuilder(phone);
            userDTO.setPhone(phoneFuffer.replace(3, phone.length() - 4, "****").toString());
        }
        LOGGER.info("{}", userDTO);
        return userDTO;
    }

    @Override
    public UserBO selectByUsernameOrPhone(String usernameOrPhone) {
        LOGGER.info("{}", usernameOrPhone);
        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(usernameOrPhone);
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user != null) {
            UserBO userDTO = new UserBO();
            BeanUtils.copyProperties(user, userDTO);
            //用户重要信息模糊化处理:电话号码
            if (!StringUtils.isEmpty(userDTO.getPhone()) && userDTO.getPhone().length() >= 8) {
                String phone = userDTO.getPhone();
                StringBuilder phoneFuffer = new StringBuilder(phone);
                user.setPhone(phoneFuffer.replace(3, phone.length() - 4, "****").toString());
            }
            userDTO.setPassword(null);
            LOGGER.info("{}", userDTO);
            return userDTO;
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserBO delete(String userId) {
        LOGGER.info("{}", userId);
        User user = userRoMapper.selectOne(userId);
        if (user != null) {
            int result = userMapper.delete(userId);
            if (result > 0) {
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(user, userBO);
                LOGGER.info("{}", userBO);
                return userBO;
            }
        }
        return null;
    }

    @Override
    public UserBO authAndRefreshToken(String token) {
        LOGGER.info("{}", token);
        UserBO user = userRoMapper.selectOneByToken(token);
        if (user != null) {
            tokenMapper.updateLastTokenResetTime(token);
            //用户重要信息模糊化处理:电话号码
            if (!StringUtils.isEmpty(user.getPhone()) && user.getPhone().length() >= 8) {
                String phone = user.getPhone();
                StringBuilder phoneFuffer = new StringBuilder(phone);
                user.setPhone(phoneFuffer.replace(3, phone.length() - 4, "****").toString());
            }
            user.setPassword(null);
        }
        return user;
    }

    @Transactional(value = "db1TxManager", rollbackFor = ServiceException.class)
    @Override
    public Boolean updatePassword(PasswordUpdateBO passwordUpdateBO, HttpServletRequest request) {
        LOGGER.info("{}", passwordUpdateBO);
        String userId = Utils.getUserId();
        //判断用户是否存在
        User userExist = userRoMapper.selectOne(userId);
        if (userExist == null) {
            throw new ServiceException(4018);
        }

        //判断是否有用户token请求头
        String token = request.getHeader(Constant.USER_TOKEN_HEAD);
        if (token == null || token.equals("")) {
            throw new ServiceException(4199);
        }

        //判断库表是否存在该token
        Token tokenExist = tokenRoMapper.isAuthentication(token);
        if (tokenExist == null) {
            throw new ServiceException(4179);
        }

        //判断user-token是否与被修改用户是同一个
        if (!userExist.getId().equals(tokenExist.getUserId())) {
            throw new ServiceException(4191);
        }

        //密码加密
        //String encodePassword = PasswordUtils.encodePassword(passwordUpdateBO.getPassword(), userExist.getSalt());

        //新的加密
        String encodePassword = rsaService.decode(passwordUpdateBO.getPassword());

        //修改密码不能为旧密码
        if (encodePassword.equals(userExist.getPassword())) {
            throw new ServiceException(4040);
        }

        //改库..
        User user = new User();
        user.setId(userExist.getId());
        user.setPassword(encodePassword);
        user.setLastUpdate(new Date());
        int result = userMapper.updatePassword(user);
        if (result != 1) {
            throw new ServiceException(4023);
        }
        //删除token
        tokenMapper.delete(token);

        try {
            //发消息
            userFeedbackMsgService.updatePasswordSuccessNotice();
            //首次修改密码任务埋点
            todoTaskService.doTask(userExist.getId(), TaskConstant.SYS_TASK_FIRST_UPDATE_PASSWROD_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public List analysisTagName(String tagName, String sliptor) {
        String[] tags = tagName.trim().split(sliptor);
        List list = Arrays.asList(tags);
        //去除空的元素
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null || list.get(i).equals("")) {
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public void updateUserVipInfo(String userId, String vipLevel) {
        if (StringUtils.isEmpty(vipLevel)) {
            LOGGER.info("更新会员失败，因为传入的用户等级编码不在约定之中：{}", vipLevel);
            return;
        }
        if (!vipLevel.trim().equals(Constant.USER_VIP_LEVEL_1) && !vipLevel.trim().equals(Constant.USER_VIP_LEVEL_2)
                && !vipLevel.trim().equals(Constant.USER_VIP_LEVEL_3) && !vipLevel.trim().equals(Constant.USER_VIP_LEVEL_4)){
            LOGGER.info("更新会员失败，因为传入的用户等级编码不在约定之中：{}", vipLevel);
            return;
        }
        //会员到期日为明年的今天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1); // 年份加1
        calendar.add(Calendar.MONTH, 0);// 月份不变
        calendar.add(Calendar.DATE, 0);// 日期不变
        //时分秒设为：23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        User userTmp = userRoMapper.selectOne(userId);
        if (userTmp == null) {
            throw new ServiceException(4018);
        }

        //用户会员等级发生变化，则会员有效时间直接覆盖原有的，否则延长一年
        if (vipLevel.trim().toUpperCase().equals(userTmp.getVipLevel())) {
            calendar.setTime(userTmp.getVipExpireDate());
            calendar.add(Calendar.YEAR, 1); // 年份加1
        }
        User user = new User();
        user.setId(userId);
        user.setVipLevel(vipLevel.trim().toUpperCase());
        user.setVipExpireDate(calendar.getTime());
        user.setLastUpdate(new Date());
        userMapper.update(user);
    }

    @Override
    public UserBO selectByopenid(String openid) {
        return userRoMapper.selectByopenid(openid);
    }

    @Override
    public void automaticUserCancel() {
        List<User> userList = userRoMapper.selectUserVipList(new Date());
        LOGGER.info("VIP到期，自动取消", userList);
        for (User user : userList) {
            LOGGER.info("VIP到期，取消用户", user);
            // 更新会员状态
            user.setVipLevel(Constant.USER_ORIGINAL_LEVEL);
            user.setLastUpdate(new Date());
            userMapper.update(user);

            // 插入会员日志
            VipLogBO bo = new VipLogBO();
            bo.setLevelId(user.getVipLevel());
            bo.setSource("系统管理员");
            bo.setUserId(user.getId());
            vipLogService.insert(bo);
        }
    }

    @Override
    public UserSimpleInfoBO selectSimple(String userId) {
        return userRoMapper.selectSimple(userId);
    }

    @Override
    public void bindPhone(BindPhoneBO bindPhoneBO) {
        LOGGER.info("绑定手机输入信息：{}", bindPhoneBO.toString());
        User user = userRoMapper.selectOne(bindPhoneBO.getUserId());
        //判断用户是否存在
        if (user == null) {
            throw new ServiceException(4018);
        }

        //新的绑定手机是否已被绑定过
        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(bindPhoneBO.getNewPhone());
        User userPhoneExist = userRoMapper.selectByUsernameOrPhone(loginBO);
        //该手机号码已被绑定
        if (userPhoneExist != null) {
            throw new ServiceException(4858);
        }

        //如果用户已绑定电话，则对传入的旧电话号码进行校验
        if (user.getPhone() != null) {
            //旧的手机号码不能为空
            if (StringUtils.isEmpty(bindPhoneBO.getOldPhone())) {
                throw new ServiceException(4856);
            }
            //旧手机号码需和已绑定手机一致
            if (!bindPhoneBO.getOldPhone().equals(user.getPhone())) {
                throw new ServiceException(4857);
            }
            //新的绑定手机号码和旧的是同一个号码
            if (bindPhoneBO.getOldPhone().equals(bindPhoneBO.getNewPhone())) {
                throw new ServiceException(4859);
            }
        }

        //验证码校验
        VerifyingCodeBO verifyingCodeBO = new VerifyingCodeBO();
        verifyingCodeBO.setPhone(bindPhoneBO.getNewPhone());
        verifyingCodeBO.setType(bindPhoneBO.getType());
        verifyingCodeBO.setCode(bindPhoneBO.getCode());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        if (!authService.verifyCode(verifyingCodeBO, request)) {
            throw new ServiceException(4201);
        }

        UserPhoneBO userPhoneBO = new UserPhoneBO();
        userPhoneBO.setId(user.getId());
        userPhoneBO.setPhone(bindPhoneBO.getNewPhone());
        LOGGER.info("用户绑定手机号：{}", userPhoneBO.toString());
        updatePhone(userPhoneBO);
    }

    @Override
    public void loginedSendCode(LoginedSendCodeBO sendCodeBO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        User user = userRoMapper.selectOne(sendCodeBO.getUserId());
        if (user == null) {
            throw new ServiceException(4018);
        }
        if (user.getPhone() == null || user.getPhone().trim().equals("")) {
            throw new ServiceException(4184);
        }

        //不变参数
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/getcode";

        //请求头设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", user.getPhone());
        requestBody.put("type", sendCodeBO.getType());

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
                //return true;
            }
        }
    }

    @Override
    public UserBO updatePhone(UserPhoneBO bo) {
        User user = userRoMapper.selectOne(bo.getId());
        if (user == null) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4018);
        }
        user.setLastUpdate(new Date());
        user.setPhone(bo.getPhone());
        int result = userMapper.updatePhone(user);
        if (result != 1) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
        }

        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public void phoneLoginSendCode(SendPhoneCodeParam sendCodeBO) {
        LOGGER.info("用户手机+验证码登录获取手机验证码参数：{}", sendCodeBO.toString());
        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(sendCodeBO.getPhone());
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        //判断手机号码是否注册
        if (user == null) {
            LOGGER.warn("此号码未注册，不允许通过验证码登录：{}", loginBO.toString());
            throw new ServiceException(4823);
        }
        //判断用户是否激活状态
        if (!user.getStatus()) {
            throw new ServiceException(4038);
        }
        //判断用户的会员身份
        if (user.getVipLevel() == null || user.getVipLevel().equals(Constant.USER_ORIGINAL_LEVEL)) {
            throw new ServiceException(4824);
        }
        //判断用户会员是否有效
        if (user.getVipExpireDate().getTime() < System.currentTimeMillis()) {
            throw new ServiceException(4825);
        }

        //发送短信
        sendPhoneCode(sendCodeBO.getPhone(), sendCodeBO.getType());
    }

    //调用message接口发送短信
    private void sendPhoneCode(String phone, String type) {
        //不变参数
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/getcode";

        //请求头设置
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constant.VERSION_HEAD, request.getHeader(Constant.VERSION_HEAD));
        httpHeaders.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", phone);
        requestBody.put("type", type);

        HttpEntity requestEntity = new HttpEntity(requestBody, httpHeaders);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }

        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            BaseObject object = JSON.parseObject(String.valueOf(responseEntity.getBody()), BaseObject.class);
            if (!object.getCode().equals("2000")) {
                throw new ServiceException(4204);
            }
        }
    }

    @Override
    public void loginedVerifyCode(LoginedVerifyCodeBO verifyCodeBO) {
        LOGGER.info("用户通过用户ID校验手机验证码，参数：{}", verifyCodeBO.toString());
        User user = userRoMapper.selectOne(verifyCodeBO.getUserId());
        if (user == null) {
            throw new ServiceException(4018);
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new ServiceException(4184);
        }

        VerifyingCodeBO codeBO = new VerifyingCodeBO();
        codeBO.setCode(verifyCodeBO.getCode());
        codeBO.setType(verifyCodeBO.getType());
        codeBO.setPhone(user.getPhone());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        boolean result = false;
        try {
            result = authService.verifyCode(codeBO, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!result) {
            throw new ServiceException(4201);
        }
    }

    @Override
    public void verifyOldPhone(oldPhoneBO oldPhone) {
        String userId = Utils.getUserId();
        User userNow = userRoMapper.selectOne(userId);


        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(oldPhone.getOldPhone());
        User user = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (user == null || !userNow.getPhone().equals(oldPhone.getOldPhone())) {
            LOGGER.warn("旧手机校验不通过：", oldPhone);
            throw new ServiceException(4826);
        }
    }

    @Override
    public IsRealNameBO isRealName() {
        IsRealNameBO isRealName = new IsRealNameBO();
        String userId = Utils.getUserId();
        UserExtend userExtend = userExtendRoMapper.isRealName(userId);
        if (userExtend != null && userExtend.getValidStatus() != null && userExtend.getValidStatus().equals
                (TaskConstant.USER_REALNAME_VALIDATED)) {
            isRealName.setIsRealName(true);
        } else {
            isRealName.setIsRealName(false);
        }
        return isRealName;
    }

    @Override
    public Map selectOneForAdmin(String userId) {
        LOGGER.info("{}", userId);
        User userTemp = userRoMapper.selectOne(userId);
        UserExtend userExtend = userExtendRoMapper.selectOneForAdmin(userId);
        if (userTemp != null) {
            UserBO user = new UserBO();
            BeanUtils.copyProperties(userTemp, user);
            user.setPassword(null);
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("user_extend", userExtend);
            LOGGER.info("{}：{}", user, userExtend);
            return map;
        }
        return null;
    }

    //获取总用户数
    @Override
    public int getAllNomalCont() {
        return userRoMapper.getAllNomalCont();
    }

    @Override
    public List<UserBO> getNomalList(Map<String, Object> map) {
        return userRoMapper.getNomalList(map);
    }

    @Override
    @Transactional("db1TxManager")
    public int changeWxBdxx(UserUpdateBO userUpdateDTO) {
        User users = new User();
        users.setId(userUpdateDTO.getId());
        users.setWxopenid(userUpdateDTO.getWxopenid());

        User user = userRoMapper.selectByWxUserId(users);
        if (user != null) {
            LOGGER.info("微信已绑定此账号");
            return 1;
        } else {
            LOGGER.info("微信绑定账号与此账号不符合，更新绑定关系");

            users.setWxheadimg(userUpdateDTO.getWxheadimg());
            users.setWxnickname(userUpdateDTO.getWxnickname());
            userMapper.qxwxbd(userUpdateDTO.getWxopenid());
            int n = userMapper.update(users);
            if (n >= 1) {
                LOGGER.info("用户关注公众号，做任务，USERID:" + userUpdateDTO.getId());
                todoTaskService.doTask(userUpdateDTO.getId(), TaskConstant.SYS_TASK_GZCSZJGZH_CODE);
                return 2;
            } else {
                throw new ServiceException(4624);
            }
        }

    }

    @Override
    public List<User> findByHngsNsrsbh(String nsrsbh) {
        return userRoMapper.findByHngsNsrsbh(nsrsbh);
    }
}
