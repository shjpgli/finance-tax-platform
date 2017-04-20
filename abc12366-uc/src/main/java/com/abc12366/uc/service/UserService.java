package com.abc12366.uc.service;


import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:15 AM
 * @since 1.0.0
 */
public interface UserService {

    List<UserBO> selectList();

    UserBO selectOne(Long id);

    UserBO update(UserUpdateBO userUpdateBO);

    UserBO selectByUsernameOrPhone(String usernameOrPhone);
}
