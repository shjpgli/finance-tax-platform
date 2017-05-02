package com.abc12366.gateway.mapper.db1;

import com.abc12366.gateway.model.Api;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 11:09 AM
 * @since 1.0.0
 */
public interface ApiMapper {
    void insert(Api api);

    void update(Api api);

    void delete(String id);
}
