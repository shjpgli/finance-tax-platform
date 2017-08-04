package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.WxGzhMapper;
import com.abc12366.uc.mapper.db2.WxGzhRoMapper;
import com.abc12366.uc.model.weixin.bo.gzh.GzhInfo;
import com.abc12366.uc.service.IWxGzhService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class WxGzhServiceimpl implements IWxGzhService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxGzhServiceimpl.class);
    @Autowired
    private WxGzhMapper wxGzhMapper;
    @Autowired
    private WxGzhRoMapper wxGzhRoMapper;


    public List<GzhInfo> wxgzhList(GzhInfo gzhInfo, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<GzhInfo> gzhInfos = wxGzhRoMapper.selectList(gzhInfo);
        return gzhInfos;
    }


    public GzhInfo selectOne(String id) {
        GzhInfo info = new GzhInfo();
        try {
            LOGGER.info("查询单个公众号信息:{}", id);
            info = wxGzhRoMapper.selectOne(id);
        } catch (Exception e) {
            LOGGER.error("查询单个公众号信息异常：{}", e);
            throw new ServiceException(4234);
        }
        return info;
    }

    @Transactional("db1TxManager")
    public GzhInfo insert(GzhInfo gzhInfo) {
        Timestamp now = new Timestamp(new Date().getTime());
        gzhInfo.setId(Utils.uuid());
        gzhInfo.setCreatDate(now);
        gzhInfo.setLastupdate(now);

        wxGzhMapper.insert(gzhInfo);
        return gzhInfo;
    }


    @Transactional("db1TxManager")
    public GzhInfo update(GzhInfo gzhInfo) {
        Timestamp now = new Timestamp(new Date().getTime());
        gzhInfo.setLastupdate(now);
        int update = wxGzhMapper.update(gzhInfo);
        if (update != 1) {
            LOGGER.info("{修改微信公众号失败}", update);
            throw new ServiceException(4421);
        }
        return wxGzhRoMapper.selectOne(gzhInfo.getId());
    }


    @Transactional("db1TxManager")
    public void delete(String id) {
        GzhInfo gzhInfo = selectOne(id);
        if (gzhInfo != null) {
            wxGzhMapper.delete(id);
        } else {
            throw new ServiceException(4012);
        }
    }


}
