package com.abc12366.uc.mapper.db1;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface ActiveUserMapper {

    List<ActiveUser> selectList();

    ActiveUser selectOne(String id);

    int delete(String id);

    int insert(ActiveUser user);
}
