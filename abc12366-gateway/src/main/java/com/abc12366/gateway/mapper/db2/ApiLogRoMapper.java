package com.abc12366.gateway.mapper.db2;

import com.abc12366.gateway.model.ApiLog;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-28 3:47 PM
 * @since 1.0.0
 */
public interface ApiLogRoMapper {
    List<ApiLog> selectList();

    List<ApiLog> selectListPage(ApiLog apiLog);

    Integer selectApiLogCount(ApiLog apiLog);


}
