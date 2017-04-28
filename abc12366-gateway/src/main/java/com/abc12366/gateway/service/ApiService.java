package com.abc12366.gateway.service;

import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 10:59 AM
 * @since 1.0.0
 */
public interface ApiService {
    List<Api> selectList();

    Api insert(ApiBO apiBO);

    Api update(ApiBO apiUpdateBO);

    void delete(String id);

    Api selectOne(String id);
}
