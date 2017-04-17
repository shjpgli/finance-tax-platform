package com.abc12366.gateway.mapper;


import com.abc12366.gateway.model.App;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:26 PM
 * @since 1.0.0
 */
public interface AppMapper {

    App selectByName(String name);

    int insert(App newApp);

    App selectOne(String id);
}
