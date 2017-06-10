package com.abc12366.cms.service;

import com.abc12366.cms.model.bo.SiteIssueBo;

import java.util.List;

public interface SiteIssueService {

    List<SiteIssueBo> selectList();

    SiteIssueBo save(SiteIssueBo siteIssueBo);

    SiteIssueBo update(SiteIssueBo siteIssueBo);

    SiteIssueBo selectOneById(Long issueId);
}
