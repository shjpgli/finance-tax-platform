package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db1.ApiLogMapper;
import com.abc12366.gateway.model.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 11:48 AM
 * @since 1.0.0
 */
@Service
public class ApiLogServiceImpl implements ApiLogService {

    @Autowired
    private ApiLogMapper apiLogMapper;

    @Override
    public void insert(ApiLog log) {
        apiLogMapper.insert(log);
    }
}
