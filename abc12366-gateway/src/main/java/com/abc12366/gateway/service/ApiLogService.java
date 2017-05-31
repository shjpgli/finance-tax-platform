package com.abc12366.gateway.service;


import com.abc12366.gateway.model.ApiLog;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 11:48 AM
 * @since 1.0.0
 */
public interface ApiLogService {
    void insert(ApiLog log);

    List<ApiLog> selectList();

    List<ApiLog> selectList(ApiLog apiLog);

    int selectApiLogCount(ApiLog apiLog);
}
