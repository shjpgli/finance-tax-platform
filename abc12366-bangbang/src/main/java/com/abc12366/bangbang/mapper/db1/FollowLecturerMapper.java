package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.FollowLecturer;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * FollowLecturerMapper数据库操作接口类
 * 
 **/

public interface FollowLecturerMapper{



	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int delete(String s, @Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(FollowLecturer record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(FollowLecturer record);

}