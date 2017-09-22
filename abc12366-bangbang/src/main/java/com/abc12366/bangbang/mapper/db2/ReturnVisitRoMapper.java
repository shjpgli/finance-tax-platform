package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.ReturnVisit;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ReturnVisitMapper数据库操作接口类
 * 
 **/

public interface ReturnVisitRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ReturnVisit  selectByPrimaryKey(@Param("id") String id);


}