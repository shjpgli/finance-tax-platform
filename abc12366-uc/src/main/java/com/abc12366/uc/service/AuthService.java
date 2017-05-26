package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.UserReturnBO;

import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:03 PM
 * @since 1.0.0
 */
public interface AuthService {

    UserReturnBO register(RegisterBO registerBO);

    Map login(LoginBO loginBO, String token) throws Exception;

    String refresh(String oldToken);

    boolean isAuthentication(String userToken);

    boolean refreshToken(String token);
}
