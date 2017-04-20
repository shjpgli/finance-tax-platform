package com.abc12366.uc.service;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-22 10:15 AM
 * @since 1.0.0
 */
public interface ActiveUserService {

    List<ActiveUser> selectList();

    ActiveUser selectOne(String id);

    int delete(String id);

    ActiveUser insert(ActiveUser user);
}
