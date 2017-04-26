package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentAttr;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentAttrMapper数据库操作接口类
 * 
 **/

public interface ContentAttrRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentAttr  selectByPrimaryKey(@Param("id") Long id);


}