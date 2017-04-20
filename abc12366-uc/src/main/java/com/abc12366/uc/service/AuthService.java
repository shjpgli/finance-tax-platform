package com.abc12366.uc.service;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 4:03 PM
 * @since 1.0.0
 */
public interface AuthService {

    UserBO register(RegisterBO registerBO);

    String login(LoginBO loginBO);

    String refresh(String oldToken);
}
