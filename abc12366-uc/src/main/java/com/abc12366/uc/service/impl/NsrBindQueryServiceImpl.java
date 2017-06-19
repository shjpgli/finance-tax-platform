package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.NsrBindQueryRoMapper;
import com.abc12366.uc.model.bo.NsrBindQueryBO;
import com.abc12366.uc.model.bo.NsrBindQueryParamBO;
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
        /*List<NsrBindQueryBO> list = nsrBindQueryRoMapper.selectDzsbBindList(nsrBindQueryParamBO);
        List<NsrBindQueryBO> list2 = nsrBindQueryRoMapper.selectHndsBindList(nsrBindQueryParamBO);
        List<NsrBindQueryBO> list3 = nsrBindQueryRoMapper.selectHngsBindList(nsrBindQueryParamBO);
        list.addAll(list2);
        list.addAll(list3);*/
        return list;
    }
}
