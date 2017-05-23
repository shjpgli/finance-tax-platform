package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.SiteMapper;
import com.abc12366.cms.mapper.db2.SiteRoMapper;
import com.abc12366.cms.model.Site;
import com.abc12366.cms.model.bo.SiteBo;
import com.abc12366.cms.model.bo.SiteListBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xieyanmao
 * @create 2017-05-18 3:10 PM
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
    public List<SiteListBo> selectList() {
        List<Site> siteList = siteRoMapper.selectList();
        List<SiteListBo> siteListBo = new ArrayList<SiteListBo>();
        for (Site site : siteList) {
            SiteListBo siteBo = new SiteListBo();
            try {
                BeanUtils.copyProperties(site, siteBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
            siteListBo.add(siteBo);
        }
        LOGGER.info("{}", siteListBo);
        return siteListBo;
    }

    @Override
    public SiteBo selectOneById(String siteId) {
        Site site = siteRoMapper.selectByPrimaryKey(siteId);
        SiteBo siteBo = new SiteBo();
        try {
            try {
                BeanUtils.copyProperties(site, siteBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        LOGGER.info("{}", siteBo);
        return siteBo;
    }

    @Override
    public SiteBo save(SiteBo siteBo) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        siteBo.setSiteId(uuid);
        Site site = new Site();
        try {
            BeanUtils.copyProperties(siteBo, site);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        int count = siteMapper.insert(site);
        LOGGER.info("{}", count);
        return siteBo;
    }

    @Override
    public SiteBo update(SiteBo siteBo) {
        Site site = new Site();
        try {
            BeanUtils.copyProperties(siteBo, site);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        int count = siteMapper.updateByPrimaryKey(site);
        LOGGER.info("{}", count);
        return siteBo;
    }


}
