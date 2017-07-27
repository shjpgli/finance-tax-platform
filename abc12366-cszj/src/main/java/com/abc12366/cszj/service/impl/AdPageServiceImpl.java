package com.abc12366.cszj.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.mapper.db1.AdPageMapper;
import com.abc12366.cszj.mapper.db2.AdPageRoMapper;
import com.abc12366.cszj.model.bo.AdPageBO;
import com.abc12366.cszj.service.AdPageService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * 广告图片管理实现类
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:21 PM
 * @since 1.0.0
 */
@Service
public class AdPageServiceImpl implements AdPageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdPageServiceImpl.class);
    // 投票
    @Autowired
    private AdPageRoMapper adPageRoMapper;

    @Autowired
    private AdPageMapper adPageMapper;

    @Override
    public List<AdPageBO> selectList(AdPageBO adPage, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<AdPageBO> adPageList = adPageRoMapper.selectList(adPage);
        return adPageList;
    }

    @Transactional("db1TxManager")
    @Override
    public AdPageBO insert(AdPageBO adPage) {
        Timestamp now = new Timestamp(new Date().getTime());
        adPage.setId(Utils.uuid());
        adPage.setCreateTime(now);
        adPage.setLastUpdate(now);

        adPageMapper.insert(adPage);
        return adPage;
    }

    @Override
    public AdPageBO selectOne(String id) {
        AdPageBO adPage = new AdPageBO();
        try {
            LOGGER.info("查询单个广告图片管理信息:{}", id);
            adPage = adPageRoMapper.selectOne(id);
        } catch (Exception e) {
            LOGGER.error("查询单个广告图片管理异常：{}", e);
            throw new ServiceException(4234);
        }
        return  adPage;
    }

    @Override
    public AdPageBO selectOneForqt(String id) {
        AdPageBO adPage = new AdPageBO();
        try {
            LOGGER.info("查询单个广告管理信息:{}", id);
            adPage = adPageRoMapper.selectOne(id);
        } catch (Exception e) {
            LOGGER.error("查询单个广告管理异常：{}", e);
            throw new ServiceException(4234);
        }
        return  adPage;
    }

    @Transactional("db1TxManager")
    @Override
    public AdPageBO update(AdPageBO adPage) {
        Timestamp now = new Timestamp(new Date().getTime());
        adPage.setLastUpdate(now);
        int update = adPageMapper.update(adPage);
        if(update != 1){
            if (update != 1){
                LOGGER.info("{修改广告图片失败}", update);
                throw new ServiceException(4421);
            }
        }
        return adPageRoMapper.selectOne(adPage.getId());
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        AdPageBO v = selectOne(id);
        if (v != null) {
            // 删除投票信息
            adPageMapper.deleteByPrimaryKey(id);
        } else {
            throw new ServiceException(4012);
        }
    }

}
