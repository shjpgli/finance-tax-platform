package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("user_extend", user_extend);
            LOGGER.info("{}", map);
            return map;
        }
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public UserBO update(UserUpdateBO userUpdateBO) {
        LOGGER.info("{}", userUpdateBO);
        User user = userRoMapper.selectOne(userUpdateBO.getId());
        if (user == null) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
        }
        BeanUtils.copyProperties(userUpdateBO, user);

        user.setLastUpdate(new Date());
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
        }
        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
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
    public UserBO selectOneByToken(String userToken) {
        LOGGER.info("{}", userToken);
        return userRoMapper.selectOneByToken(userToken);
    }

    @Override
    public Boolean updatePassword(PasswordUpdateBO passwordUpdateBO) {
        LOGGER.info("{}", passwordUpdateBO);
        LoginBO loginBO = new LoginBO();
        loginBO.setUsernameOrPhone(passwordUpdateBO.getPhone());
        User userExist = userRoMapper.selectByUsernameOrPhone(loginBO);
        if (userExist == null) {
            throw new ServiceException(4018);
        }

        //密码加密
        String password;
        String encodePassword;
        String salt;
        try {
            //密码生产规则：前台传密码md5之后的值，后台用该值加上salt再md5 ，salt是随机生成的六位整数
            password = Utils.md5(passwordUpdateBO.getPassword());
            salt = Utils.salt();
            encodePassword = Utils.md5(password + salt);
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + e);
            throw new ServiceException(4106);
        }

        User user = new User();
        user.setId(userExist.getId());
        user.setPhone(passwordUpdateBO.getPhone());
        user.setPassword(encodePassword);
        int result = userMapper.update(user);
        if (result != 1) {
            throw new ServiceException(4023);
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
}
