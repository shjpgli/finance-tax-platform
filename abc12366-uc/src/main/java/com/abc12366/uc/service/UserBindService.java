package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserDzsbBO;
import com.abc12366.uc.model.bo.UserHndsBO;
import com.abc12366.uc.model.bo.UserHngsBO;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public interface UserBindService {
    UserDzsbBO dzsbBind(UserDzsbBO userDzsbBO);

    boolean dzsbUnbind(String id);

    UserHngsBO hngsBind(UserHngsBO userHngsBO);

    boolean hngsUnbind(String id);

    UserHndsBO hndsBind(UserHndsBO userHndsBO);

    boolean hndsUnbind(String id);
}
