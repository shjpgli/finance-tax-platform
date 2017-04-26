package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentCheck;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentCheckMapper数据库操作接口类
 * 
 **/

public interface ContentCheckRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentCheck  selectByPrimaryKey(@Param("id") Long id);


}