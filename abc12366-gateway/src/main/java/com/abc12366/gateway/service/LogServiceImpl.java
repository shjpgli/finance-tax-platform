package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db1.LogMapper;
import com.abc12366.gateway.model.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 11:48 AM
 * @since 1.0.0
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void insert(ApiLog log) {
        logMapper.insert(log);
    }
}
