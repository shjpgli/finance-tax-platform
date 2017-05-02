package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.Role;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * RoleMapper数据库操作接口类
 * 
 **/

public interface RoleRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Role  selectByPrimaryKey(@Param("id") Long id);

}