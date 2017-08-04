package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.admin.Admin;
import com.abc12366.uc.model.admin.bo.UserBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * UserMapper数据库操作接口类
 * 
 **/

public interface AdminRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Admin selectByPrimaryKey(@Param("id") String id);


	Admin selectUserByLoginName(String username);

	UserBO selectUserBOByLoginName(String username);

	Admin selectUserById(String id);

	UserBO selectUserBoById(String id);

	List<UserBO> selectList(Admin admin);

    UserBO selectOne(@Param("id") String id);
}