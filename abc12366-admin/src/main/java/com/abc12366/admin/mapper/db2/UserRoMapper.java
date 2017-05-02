package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.User;
import com.abc12366.admin.vo.UserVO;
import org.apache.ibatis.annotations.Param;

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

	User selectUserById(String id);

	UserVO selectUserVoById(String id);
}