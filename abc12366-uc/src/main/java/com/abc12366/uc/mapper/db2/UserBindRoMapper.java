package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.bo.UserDzsbListBO;
import com.abc12366.uc.model.bo.UserHndsBO;
import com.abc12366.uc.model.bo.UserHngsListBO;

import java.util.List;

/**
 *
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindRoMapper {

    UserDzsb userDzsbSelectById(String id);

    UserHngs userHngsSelectById(String id);

    UserHnds userHndsSelectById(String id);

    List<UserDzsbListBO> getUserDzsbBind(String userId);

    List<UserHngsListBO> getUserhngsBind(String userId);

    List<UserHndsBO> getUserhndsBind(String userId);
}
