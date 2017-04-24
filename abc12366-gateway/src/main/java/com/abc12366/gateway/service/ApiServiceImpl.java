package com.abc12366.gateway.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.ApiMapper;
import com.abc12366.gateway.mapper.db2.ApiRoMapper;
import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 3:24 PM
 * @since 1.0.0
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private ApiRoMapper apiRoMapper;

    @Override
    public List<Api> selectList() {
        return apiRoMapper.selectList();
    }

    @Override
    public Api selectOne(String id) {
        return apiRoMapper.selectOne(id);
    }

    @Override
    public Api insert(ApiBO apiBO) {
        Api api = new Api.Builder()
                .id(Utils.uuid())
                .name(apiBO.getName())
                .mark(apiBO.getMark())
                .method(apiBO.getMethod())
                .role(apiBO.getRole())
                .version(apiBO.getVersion())
                .appId(apiBO.getAppId())
                .status(apiBO.getStatus())
                .createDate(new Date())
                .modifyDate(new Date())
                .build();

        int rows = apiMapper.insert(api);
        return rows > 0 ? api : null;
    }

    @Override
    public Api update(ApiBO apiBO) {
        Assert.notNull(apiBO.getId(), "Api id can not empty");
        Api api = selectOne(apiBO.getId());

        api.setName(apiBO.getName());
        api.setMark(apiBO.getMark());
        api.setMethod(apiBO.getMethod());
        api.setRole(apiBO.getRole());
        api.setVersion(apiBO.getVersion());

        api.setAppId(apiBO.getAppId());
        api.setStatus(apiBO.getStatus());
        api.setModifyDate(new Date());

        int rows = apiMapper.update(api);
        return rows > 0 ? api : null;
    }

    @Override
    public int delete(ApiBO apiBO) {
        Assert.notNull(apiBO.getId(), "Api id can not empty");
        Api api = selectOne(apiBO.getId());

        int rows = 0;
        if (api != null) {
            api.setStatus("0");
            api.setModifyDate(new Date());
            rows = apiMapper.update(api);
        }
        return rows;
    }
}
