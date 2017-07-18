package com.abc12366.gateway.service;

import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.ApiMapper;
import com.abc12366.gateway.mapper.db2.ApiRoMapper;
import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 11:00 AM
 * @since 1.0.0
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiRoMapper apiRoMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public List<ApiBO> selectList(Api api) {
        return apiRoMapper.selectList(api);
    }

    @Override
    public Api insert(ApiBO apiBO) {
        Api api = new Api();
        BeanUtils.copyProperties(apiBO, api);
        ApiBO queryApi = apiRoMapper.selectOne(api);
        if (queryApi != null) {
            return null;
        }

        api.setId(Utils.uuid());
        Date now = new Date();
        api.setCreateTime(now);
        api.setLastUpdate(now);
        apiMapper.insert(api);
        return api;
    }

    @Override
    public Api update(ApiBO apiBO) {
        Api api = new Api.Builder()
                .id(apiBO.getId())
                .build();

        ApiBO updateApi = apiRoMapper.selectOne(api);
        if (updateApi != null) {
            updateApi.setAppId(apiBO.getAppId());
            updateApi.setName(apiBO.getName());
            updateApi.setUri(apiBO.getUri());
            updateApi.setMethod(apiBO.getMethod());
            updateApi.setVersion(apiBO.getVersion());
            updateApi.setAuthentication(apiBO.isAuthentication());
            updateApi.setStatus(apiBO.isStatus());
            updateApi.setLastUpdate(new Date());
            apiMapper.update(updateApi);
            return api;
        }

        return null;
    }

    @Override
    public void delete(String id) {
        Api api = new Api();
        api.setId(id);
        ApiBO deleteApi = apiRoMapper.selectOne(api);
        if (deleteApi != null) {
            apiMapper.delete(id);
        }
    }

    @Override
    public ApiBO selectOne(String id) {
        Api api = new Api.Builder()
                .id(id)
                .build();

        return apiRoMapper.selectOne(api);
    }
}
