package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:03 PM
 * @since 1.0.0
 */
public interface AuthService {

    UserReturnBO register(RegisterBO registerBO);

    Map login(LoginBO loginBO, String token) throws Exception;

    Map loginJs(LoginBO loginBO, String token) throws Exception;

    String refresh(String oldToken);

    boolean isAuthentication(String userToken, HttpServletRequest request);

    boolean refreshToken(String token);

    Map loginByVerifyingCode(VerifyingCodeBO loginBO, String header) throws Exception;

    boolean verifyCode(VerifyingCodeBO loginVerifyingCodeBO, HttpServletRequest request) throws IOException;

    void logout(String token);

	Map loginByopenid(UserBO user, String header) throws Exception;
}
