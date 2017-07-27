package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.uc.mapper.db1.TokenMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.TokenRoMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.Token;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.util.PasswordUtils;
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
    public UserBO update(UserUpdateBO userUpdateBO) {
        LOGGER.info("{}", userUpdateBO);
        User user = userRoMapper.selectOne(userUpdateBO.getId());
        if (user == null) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
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
        int result = userMapper.update(user);
        if (result != 1) {
            LOGGER.warn("修改失败");
            throw new ServiceException(4102);
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
    public UserBO selectOneByToken(String userToken) {
        LOGGER.info("{}", userToken);
        return userRoMapper.selectOneByToken(userToken);
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
        String token = (String) request.getAttribute(Constant.USER_TOKEN_HEAD);
        if (token == null || token.equals("")) {
            throw new ServiceException(4199);
        }

        //判断库表是否存在该token
        Token tokenExist = tokenRoMapper.isAuthentication(token);
        if (tokenExist == null) {
            throw new ServiceException(4179);
        }

        //密码加密
        String encodePassword = PasswordUtils.encodePassword(passwordUpdateBO.getPassword());

        //改库..
        User user = new User();
        user.setId(userExist.getId());
        user.setPhone(passwordUpdateBO.getPhone());
        user.setPassword(encodePassword);
        int result = userMapper.update(user);
        if (result != 1) {
            throw new ServiceException(4023);
        }
        //删除token
        tokenMapper.delete(token);
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
