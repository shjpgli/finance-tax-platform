package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.SiteIssue;
import com.abc12366.cms.model.bo.SiteIssueBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * SiteIssueMapper数据库操作接口类
 * 
 **/

public interface SiteIssueRoMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	SiteIssue selectByPrimaryKey(@Param("issueId") Long issueId);

	/**
	 * 查询所有
	 **/
	List<SiteIssueBo> selectList();

}