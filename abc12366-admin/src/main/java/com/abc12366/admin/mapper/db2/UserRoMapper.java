package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.User;
import com.abc12366.admin.model.bo.UserBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * UserMapper数据库操作接口类
 * 
 **/

public interface UserRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	User  selectByPrimaryKey(@Param("id") String id);


	User selectUserByLoginName(String username);

	UserBO selectUserBOByLoginName(String username);

	User selectUserById(String id);

	UserBO selectUserBoById(String id);

	List<UserBO> selectList(User user);

    UserBO selectOne(@Param("id") String id);
}