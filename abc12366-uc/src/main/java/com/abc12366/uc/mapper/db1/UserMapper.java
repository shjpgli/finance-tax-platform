package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.User;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface UserMapper {

    int insert(User user);

    int update(User user);
}
