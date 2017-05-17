package com.abc12366.uc.service;


import com.abc12366.uc.model.bo.*;

import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:15 AM
 * @since 1.0.0
 */
public interface UserService {

    List<UserBO> selectList();

    Map selectOne(String userId);

    UserBO update(UserUpdateBO userUpdateBO);

    UserBO selectByUsernameOrPhone(String usernameOrPhone);

    //UserBO register(RegisterBO registerBO);

    UserBO delete(String userId);

    //String login(LoginBO loginBO, String token) throws Exception;
}
