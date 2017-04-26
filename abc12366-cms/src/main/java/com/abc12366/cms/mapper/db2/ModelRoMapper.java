package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Model;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ModelMapper数据库操作接口类
 * 
 **/

public interface ModelRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Model selectByPrimaryKey(@Param("id") Long id);


}