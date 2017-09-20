package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.mapper.db1.TokenMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.TokenRoMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.Token;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.PasswordUpdateBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;
import com.abc12366.uc.util.PasswordUtils;
import com.abc12366.uc.util.UCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:17 AM
 * @since 1.0.0
 */
@Service
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
    public Map selectOne(String userId) {
        LOGGER.info("{}", userId);
        User userTemp = userRoMapper.selectOne(userId);
        UserExtend user_extend = userExtendRoMapper.selectOne(userId);
        if (userTemp != null) {
            UserBO user = new UserBO();
            BeanUtils.copyProperties(userTemp, user);
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
        User user = userRoMapper.selectOne(userUpdateBO.getId());
        if (user == null) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4018);
        }

        //普通用户只允许修改一次用户名
        if (userUpdateBO.getUsername() != null && !userUpdateBO.getUsername().trim().equals(user.getUsername()) && user.getUsernameModifiedTimes() >= 1) {
            throw new ServiceException(4037);
        }

        //进行用户名和电话唯一性确认
        List<UserBO> userBOList = userRoMapper.selectListExcludedId(userUpdateBO.getId());
        if (userBOList != null && userBOList.size() > 1) {
            //从list移除本身
            for (int i = 0; i < userBOList.size(); i++) {
                if (userBOList.get(i).getId().trim().equals(userUpdateBO.getId().trim())) {
                    userBOList.remove(i);
                }
            }

            for (UserBO userBO : userBOList) {
                if (userUpdateBO.getUsername() != null) {
                    if (userBO.getUsername().trim().equals(userUpdateBO.getUsername().trim())) {
                        throw new ServiceException(4182);
                    }
                }
                if (userUpdateBO.getPhone() != null) {
                    if (userBO.getPhone().trim().equals(userUpdateBO.getPhone().trim())) {
                        throw new ServiceException(4183);
                    }
                }

            }
        }


        BeanUtils.copyProperties(userUpdateBO, user);

        user.setLastUpdate(new Date());
        if (user.getUsername() != null) {
            user.setUsernameModifiedTimes(user.getUsernameModifiedTimes() + 1);
        }
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
        }

        if (user.getUserPicturePath() != null && !user.getUserPicturePath().trim().equals("")) {
            //首次上传用户头像任务埋点
            todoTaskService.doTask(user.getId(),UCConstant.SYS_TASK_FIRST_UPLOAD_PICTURE_ID);
        }

        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setPassword(null);
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
        }
        return user;
    }

    @Transactional("db1TxManager")
    @Override
    public Boolean updatePassword(PasswordUpdateBO passwordUpdateBO, HttpServletRequest request) {
        LOGGER.info("{}", passwordUpdateBO);
        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(passwordUpdateBO.getPhone());
        //判断用户是否存在
        User userExist = userRoMapper.selectByUsernameOrPhone(loginBO);
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
        if(encodePassword.equals(userExist.getPassword())){
            throw new ServiceException(4040);
        }

        //改库..
        User user = new User();
        user.setId(userExist.getId());
        user.setPhone(passwordUpdateBO.getPhone());
        user.setPassword(encodePassword);
        user.setLastUpdate(new Date());
        int result = userMapper.update(user);
        if (result != 1) {
            throw new ServiceException(4023);
        }
        //删除token
        tokenMapper.delete(token);

        //首次修改密码任务埋点
        todoTaskService.doTask(userExist.getId(), UCConstant.SYS_TASK_FIRST_UPDATE_PASSWROD_ID);
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
        //会员到期日为明年的今天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1); // 年份加1
        calendar.add(Calendar.MONTH, 0);// 月份不变
        calendar.add(Calendar.DATE, 0);// 日期不变

        User userTmp = userRoMapper.selectOne(userId);
        if (userTmp == null) {
            throw new ServiceException(4018);
        }

        //用户会员等级发生变化，则会员有效时间直接覆盖原有的，否则延长一年
        if(vipLevel.trim().toUpperCase().equals(userTmp.getVipLevel())){
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
}
