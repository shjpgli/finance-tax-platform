package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.NsrBindQueryRoMapper;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.NsrBindQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:45
 */
@Service
public class NsrBindQueryServiceImpl implements NsrBindQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsrBindQueryServiceImpl.class);

    @Autowired
    private NsrBindQueryRoMapper nsrBindQueryRoMapper;
    @Override
    public List<NsrBindQueryBO> selectList(NsrBindQueryParamBO nsrBindQueryParamBO) {
        LOGGER.info("{}", nsrBindQueryParamBO);
        List<NsrBindQueryBO> list = nsrBindQueryRoMapper.selectList(nsrBindQueryParamBO);
        return list;
    }

    @Override
    public UserDzsbBO selectDzsb(String id) {
        LOGGER.info("{}", id);
        return nsrBindQueryRoMapper.selectDzsb(id);
    }

    @Override
    public UserHndsBO selectHnds(String id) {
        LOGGER.info("{}", id);
        return nsrBindQueryRoMapper.selectHnds(id);
    }

    @Override
    public UserHngsBO selectHngs(String id) {
        LOGGER.info("{}", id);
        return nsrBindQueryRoMapper.selectHngs(id);
    }
}
