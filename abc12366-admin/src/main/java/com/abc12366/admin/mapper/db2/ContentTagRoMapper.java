package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentTag;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContenttagMapper数据库操作接口类
 * 
 **/

public interface ContentTagRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentTag  selectByPrimaryKey(@Param("id") Long id);


}