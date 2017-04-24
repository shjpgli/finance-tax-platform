package com.abc12366.gateway.mapper.db2;

import com.abc12366.gateway.model.App;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 10:38 AM
 * @since 1.0.0
 */
public interface AppRoMapper {

    App selectByName(String name);

    App selectOne(String id);
}
