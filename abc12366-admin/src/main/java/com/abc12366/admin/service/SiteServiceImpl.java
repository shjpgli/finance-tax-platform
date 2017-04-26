package com.abc12366.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc12366.admin.mapper.db2.SiteRoMapper;
import com.abc12366.admin.model.cms.Site;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:10 PM
 * @since 1.0.0
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRoMapper siteRoMapper;

	@Override
	public List<Site> listSite() {
		return siteRoMapper.listSite();
	}

}
