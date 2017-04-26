package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentExt;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentExtMapper数据库操作接口类
 * 
 **/

public interface ContentExtRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentExt  selectByPrimaryKey(@Param("id") Long id);


}