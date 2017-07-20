package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.SiteMapper;
import com.abc12366.cms.mapper.db2.SiteRoMapper;
import com.abc12366.cms.model.Site;
import com.abc12366.cms.model.bo.SiteBo;
import com.abc12366.cms.model.bo.SiteListBo;
import com.abc12366.cms.service.SiteService;
import com.abc12366.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<SiteListBo> siteListBo;
        try {
            siteListBo = siteRoMapper.selectList();
        } catch (Exception e) {
            LOGGER.error("查询站点信息异常：{}", e);
            throw new ServiceException(4210);
        }
        LOGGER.info("查询站点信息结果:{}", siteListBo);
        return siteListBo;
    }

    @Override
    public SiteBo selectOneById(String siteId) {
        LOGGER.info("查询单个站点信息:{}", siteId);
        SiteBo siteBo = new SiteBo();
        try {
            Site site = siteRoMapper.selectByPrimaryKey(siteId);
            BeanUtils.copyProperties(site, siteBo);
        } catch (Exception e) {
            LOGGER.error("查询单个站点信息异常：{}", e);
            throw new ServiceException(4211);
        }
        LOGGER.info("查询单个站点信息结果:{}", siteBo);
        return siteBo;
    }

    @Override
    public SiteBo save(SiteBo siteBo) {
        LOGGER.info("新增站点信息:{}", siteBo);
        Site site = new Site();
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            siteBo.setSiteId(uuid);
            BeanUtils.copyProperties(siteBo, site);
            siteMapper.insert(site);
        } catch (Exception e) {
            LOGGER.error("新增站点信息异常：{}", e);
            throw new ServiceException(4212);
        }
        return siteBo;
    }

    @Override
    public SiteBo update(SiteBo siteBo) {
        LOGGER.info("更新站点信息:{}", siteBo);
        Site site = new Site();
        try {
            BeanUtils.copyProperties(siteBo, site);
            siteMapper.updateByPrimaryKeySelective(site);
        } catch (Exception e) {
            LOGGER.error("更新站点信息异常：{}", e);
            throw new ServiceException(4213);
        }
        return siteBo;
    }


}
