package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * UserRoleMapper数据库操作接口类
 * 
 **/

public interface UserRoleRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserRole  selectByPrimaryKey(@Param("id") Long id);

	List<UserRole> selectUserRoleByUserId(String id);
}