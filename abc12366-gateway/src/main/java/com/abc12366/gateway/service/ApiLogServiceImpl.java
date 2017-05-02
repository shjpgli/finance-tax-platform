package com.abc12366.gateway.service;

import com.abc12366.gateway.mapper.db1.ApiLogMapper;
import com.abc12366.gateway.mapper.db2.ApiLogRoMapper;
import com.abc12366.gateway.model.ApiLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 11:48 AM
 * @since 1.0.0
 */
@Service
public class ApiLogServiceImpl implements ApiLogService {

    @Autowired
    private ApiLogMapper apiLogMapper;

    @Autowired
    private ApiLogRoMapper apiLogRoMapper;

    @Override
    public void insert(ApiLog log) {
        apiLogMapper.insert(log);
    }

    @Override
    public List<ApiLog> selectList() {
        return apiLogRoMapper.selectList();
    }

    @Override
    public List<ApiLog> selectList(ApiLog apiLog) {
        return apiLogRoMapper.selectListPage(apiLog);
    }
}
