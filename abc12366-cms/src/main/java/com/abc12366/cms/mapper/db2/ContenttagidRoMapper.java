package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Contenttagid;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContenttagidMapper数据库操作接口类
 * 
 **/

public interface ContenttagidRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Contenttagid selectByPrimaryKey(@Param("id") Long id);


}