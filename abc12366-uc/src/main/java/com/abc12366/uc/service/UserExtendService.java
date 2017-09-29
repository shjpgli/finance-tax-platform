package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
public interface UserExtendService {
    UserExtendBO selectOne(String userId);

    UserExtendBO insert(UserExtendBO userExtendBO, HttpServletRequest request);

    UserExtendBO delete(String userId);

    UserExtendBO update(UserExtendUpdateBO userExtendUpdateBO, HttpServletRequest request) throws IOException;
}
