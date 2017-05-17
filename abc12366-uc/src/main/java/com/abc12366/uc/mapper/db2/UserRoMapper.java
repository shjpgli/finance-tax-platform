package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.UserBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface UserRoMapper {

    List<UserBO> selectList();

    User selectOne(String id);

    User selectByUsernameOrPhone(User user);

}
