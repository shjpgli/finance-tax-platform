package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.Content;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentMapper数据库操作接口类
 * 
 **/

public interface ContentRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Content  selectByPrimaryKey(@Param("id") Long id);


}