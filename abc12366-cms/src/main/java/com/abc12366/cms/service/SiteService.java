package com.abc12366.cms.service;


import com.abc12366.cms.model.Site;
import com.abc12366.cms.vo.SiteVO;

import java.util.List;

public interface SiteService {

    List<SiteVO> selectList();

    int update(SiteVO siteVO);

    SiteVO selectOneById(String id);
}
