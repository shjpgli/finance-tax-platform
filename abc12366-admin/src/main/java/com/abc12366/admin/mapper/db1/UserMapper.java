package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * UserMapper数据库操作接口类
 * 
 **/

public interface UserMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteById(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(User record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(User record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(User record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(User record);


	void updateUserPwdById(String userId, String pwd);

	void updateUser(User user);

}