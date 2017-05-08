package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserExtendBO;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
public interface UserExtendService {
    UserExtendBO selectOne(String userId);

    UserExtendBO insert(UserExtendBO userExtendBO);

    UserExtendBO delete(String userId);

    UserExtendBO update(UserExtendBO userExtendBO);
}
