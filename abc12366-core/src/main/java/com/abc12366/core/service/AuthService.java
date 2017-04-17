package com.abc12366.core.service;

import com.abc12366.core.model.bo.LoginBO;
import com.abc12366.core.model.bo.RegisterBO;
import com.abc12366.core.model.bo.UserBO;

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
