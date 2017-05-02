package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.RoleMenu;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * RoleMenuMapper数据库操作接口类
 * 
 **/

public interface RoleMenuRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	RoleMenu  selectByPrimaryKey(@Param("id") Long id);

}