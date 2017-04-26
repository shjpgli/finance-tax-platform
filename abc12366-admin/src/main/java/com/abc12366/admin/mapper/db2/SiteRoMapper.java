package com.abc12366.admin.mapper.db2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.Site;

/**
 * 
 * SiteMapper数据库操作接口类
 * 
 **/

public interface SiteRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Site  selectByPrimaryKey ( @Param("id") Long id );


	List<Site> listSite();

}