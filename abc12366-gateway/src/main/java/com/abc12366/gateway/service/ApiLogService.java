package com.abc12366.gateway.service;


import com.abc12366.gateway.model.ApiLog;

import java.util.List;
import java.util.Map;

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

    List<ApiLog> selectApiList(Map<String, Object> map);

    List<ApiLog> selectApiListByAppId(Map<String, Object> map);
}
