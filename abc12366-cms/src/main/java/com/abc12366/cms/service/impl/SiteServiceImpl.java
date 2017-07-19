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
        List<SiteListBo> siteListBo = siteRoMapper.selectList();
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
            throw new ServiceException(4210);
        }
        int rnt = siteMapper.insert(site);
        if(rnt != 1){
            LOGGER.error("新增站点失败：{}", site);
            throw new ServiceException(4210);
        }
        LOGGER.info("{}", rnt);
        return siteBo;
    }

    @Override
    public SiteBo update(SiteBo siteBo) {
        Site site = new Site();
        try {
            BeanUtils.copyProperties(siteBo, site);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new ServiceException(4211);
        }
        int rnt = siteMapper.updateByPrimaryKeySelective(site);
        if(rnt != 1){
            LOGGER.error("修改站点失败：{}", site);
            throw new ServiceException(4211);
        }
        LOGGER.info("{}", rnt);
        return siteBo;
    }


}
