package com.abc12366.uc.service;


import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.PasswordUpdateBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:15 AM
 * @since 1.0.0
 */
public interface UserService {

    List<UserBO> selectList(Map<String, Object> map);

    Map selectOne(String userId);

    UserBO update(UserUpdateBO userUpdateBO);

    UserBO selectByUsernameOrPhone(String usernameOrPhone);

    //AdminBO register(RegisterBO registerBO);

    UserBO delete(String userId);

    UserBO authAndRefreshToken(String token);

    Boolean updatePassword(PasswordUpdateBO passwordUpdateBO, HttpServletRequest request);
    void enableOrDisable(String id, String status);
    //String login(LoginBO loginBO, String token) throws Exception;

    public void updateUserVipInfo(String userId, String vipLevel);
}
