package com.abc12366.cszj.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.mapper.db1.AdPageMapper;
import com.abc12366.cszj.mapper.db2.AdPageRoMapper;
import com.abc12366.cszj.model.bo.AdPageBO;
import com.abc12366.cszj.service.AdPageService;
import com.github.pagehelper.PageHelper;
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
        AdPageBO adPage = adPageRoMapper.selectOne(id);
        return  adPage;
    }

    @Transactional("db1TxManager")
    @Override
    public AdPageBO update(AdPageBO adPage) {
        Timestamp now = new Timestamp(new Date().getTime());
        AdPageBO v = selectOne(adPage.getId());
        if (v != null) {
            adPage.setLastUpdate(now);
            adPageMapper.update(adPage);
            return selectOne(adPage.getId());
        } else {
            throw new ServiceException(4012);
        }
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
