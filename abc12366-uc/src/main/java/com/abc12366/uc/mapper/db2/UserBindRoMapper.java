package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public interface UserBindRoMapper {

    UserDzsb userDzsbSelectById(String id);

    UserHngs userHngsSelectById(String id);

    UserHnds userHndsSelectById(String id);

}
