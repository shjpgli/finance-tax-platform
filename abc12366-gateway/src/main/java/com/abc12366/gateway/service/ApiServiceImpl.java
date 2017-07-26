package com.abc12366.gateway.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.mapper.db1.ApiMapper;
import com.abc12366.gateway.mapper.db2.ApiRoMapper;
import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-27 11:00 AM
 * @since 1.0.0
 */
@Service
public class ApiServiceImpl implements ApiService {

    private static Logger LOGGER = LoggerFactory.getLogger(ApiServiceImpl.class);
    @Autowired
    private ApiRoMapper apiRoMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public List<ApiBO> selectList(Api api) {
        return apiRoMapper.selectList(api);
    }

    @Transactional("db1TxManager")
    @Override
    public Api insert(ApiBO apiBO) {
        //uri，version，method，确定数据的唯一性
        Api temp = new Api();
        temp.setUri(apiBO.getUri());
        temp.setVersion(apiBO.getUri());
        temp.setVersion(apiBO.getMethod());
        ApiBO bo = apiRoMapper.selectByUriAndVersion(temp);
        if(bo != null){
            LOGGER.warn("uri，version，method，确定数据的唯一性：{}", bo);
            throw new ServiceException(4030);
        }
        apiBO.setId(Utils.uuid());
        Date now = new Date();
        apiBO.setCreateTime(now);
        apiBO.setLastUpdate(now);
        Api api = new Api();
        BeanUtils.copyProperties(apiBO, api);
        int insert = apiMapper.insert(api);
        if(insert != 1){
            LOGGER.warn("插入失败，参数：{}", api);
            throw new ServiceException(4101);
        }
        return api;
    }

    @Transactional("db1TxManager")
    @Override
    public Api update(ApiBO apiBO) {
        Api api = new Api();
        BeanUtils.copyProperties(apiBO, api);
        api.setLastUpdate(new Date());
        int update = apiMapper.update(api);
        if(update != 1){
            LOGGER.warn("修改失败，参数：{}", api);
            throw new ServiceException(4102);
        }
        return api;

    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        int del = apiMapper.delete(id);
        if(del != 1){
            LOGGER.warn("修改失败，参数：{}", id);
            throw new ServiceException(4103);
        }
    }

    @Override
    public ApiBO selectOne(String id) {
        Api api = new Api();
        api.setId(id);
        return apiRoMapper.selectOne(api);
    }

    @Override
    public List<ApiBO> selectBySettingList(String appId) {
        List<ApiBO> boList = apiRoMapper.selectBySettingList(appId);
        if(boList != null && boList.size() != 0){
            return boList;
        }else{
            boList = apiRoMapper.selectList(new Api());
        }
        return boList;
    }
}
