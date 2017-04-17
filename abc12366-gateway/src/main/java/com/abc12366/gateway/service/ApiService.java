package com.abc12366.gateway.service;


import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 3:22 PM
 * @since 1.0.0
 */
public interface ApiService {
    List<Api> selectList();

    Api selectOne(String id);

    Api insert(ApiBO apiBO);

    Api update(ApiBO apiBO);

    int delete(ApiBO apiBO);
}
