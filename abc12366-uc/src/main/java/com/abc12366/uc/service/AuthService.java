package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.LoginVerifyingCodeBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.UserReturnBO;
import org.springframework.http.ResponseEntity;

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

    String refresh(String oldToken);

    boolean isAuthentication(String userToken);

    boolean refreshToken(String token);

    Map loginByVerifyingCode(LoginVerifyingCodeBO loginBO, String header) throws Exception;

    ResponseEntity verifyCode(String phone, String code) throws IOException;
}
