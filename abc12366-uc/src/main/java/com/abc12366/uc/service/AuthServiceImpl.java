package com.abc12366.uc.service;

import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.AuthorityMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.Authority;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.UserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AuthorityMapper authorityMapper;

    @Override
    public UserBO register(RegisterBO registerBO) {
        final String username = registerBO.getUsername();
        final String phone = registerBO.getPhone();
        if(userRoMapper.selectByUsernameOrPhone(username) != null || userRoMapper.selectByUsernameOrPhone(phone) != null) {
            return null;
        }
        final String rawPassword = registerBO.getPassword();

        User user = new User.Builder()
                .id(Utils.uuid())
                .username(registerBO.getUsername())
                .phone(registerBO.getPhone())
                .password(rawPassword)
                .lastPasswordResetDate(new Date())
                .roles(asList("ROLE_USER"))
                .enabled(true)
                .createDate(new Date())
                .modifyDate(new Date())
                .build();
        int rows = userMapper.insert(user);

        if (rows > 0) {
            for (String role : user.getRoles()) {
                Authority authority = new Authority.Builder()
                        .userId(user.getId())
                        .authority(role).build();
                authorityMapper.insert(authority);
            }
        }

        UserBO userDTO = new UserBO();
        BeanUtils.copyProperties(user, userDTO);
        LOGGER.info("{}", userDTO);
        return userDTO;
    }

    @Override
    public String login(LoginBO loginBO) {
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }
}
