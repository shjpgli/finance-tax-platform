package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.*;

import java.util.List;

/**
 *
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindService {
    UserDzsbBO dzsbBind(UserDzsbBO userDzsbBO);

    boolean dzsbUnbind(String id);

    UserHngsBO hngsBind(UserHngsBO userHngsBO);

    boolean hngsUnbind(String id);

    UserHndsBO hndsBind(UserHndsBO userHndsBO);

    boolean hndsUnbind(String id);

    List<UserDzsbListBO> getUserDzsbBind(String userId);

    List<UserHngsListBO> getUserhngsBind(String userId);

    List<UserHndsBO> getUserhndsBind(String userId);
}
