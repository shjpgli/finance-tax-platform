package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.SiteMapper;
import com.abc12366.cms.mapper.db2.SiteRoMapper;
import com.abc12366.cms.model.Site;
import com.abc12366.cms.service.SiteService;
import com.abc12366.cms.vo.SiteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:10 PM
 * @since 1.0.0
 */
@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private SiteRoMapper siteRoMapper;

    @Override
    public List<SiteVO> selectList() {
        List<Site> siteList = siteRoMapper.selectList();
        List<SiteVO> siteVOs = new ArrayList<SiteVO>();
        for (Site site : siteList) {
            SiteVO sVO = new SiteVO();
            BeanUtils.copyProperties(site, sVO);
            siteVOs.add(sVO);
        }
        LOGGER.info("{}", siteVOs);
        return siteVOs;
    }

    @Override
    public SiteVO selectOneById(String siteId) {
        Site site = siteRoMapper.selectOneById(siteId);
        if (site != null) {
            SiteVO siteVO = new SiteVO();
            BeanUtils.copyProperties(site, siteVO);
            LOGGER.info("{}", siteVO);
            return siteVO;
        }
        return null;
    }

    @Override
    public int update(SiteVO siteVO) {
        Site site = siteRoMapper.selectOneById(siteVO.getSiteId());
        int count = 0;
        if (site != null) {
            Site site1 = new Site();
            BeanUtils.copyProperties(siteVO, site1);
            count = siteMapper.updateByPrimaryKey(site1);
            LOGGER.info("{}", count);
            return count;
        }
        return count;
    }


}
