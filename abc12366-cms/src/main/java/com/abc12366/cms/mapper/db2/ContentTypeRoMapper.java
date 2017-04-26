package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ContentType;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentTypeMapper数据库操作接口类
 * 
 **/

public interface ContentTypeRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentType selectByPrimaryKey(@Param("id") Long id);

}