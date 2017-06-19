package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.Wiki;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * WikiMapper数据库操作接口类
 * 
 **/

public interface WikiRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Wiki  selectByPrimaryKey(@Param("id") String id);


}