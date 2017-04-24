package com.abc12366.gateway.mapper.db1;


import com.abc12366.gateway.model.Api;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 8:53 AM
 * @since 1.0.0
 */
public interface ApiMapper {

    int insert(Api api);

    int update(Api api);

    int delete(Api api);
}
