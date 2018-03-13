package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.FollowLecturer;
import com.abc12366.bangbang.model.bo.FollowLecturerBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * FollowLecturerMapper数据库操作接口类
 * 
 **/

public interface FollowLecturerRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	FollowLecturer  selectByPrimaryKey(@Param("id") String id);

    /**
     * 查询列表
     */
	List<FollowLecturerBO> selectList(Map<String, Object> map);

    /**
     * 单个查询
     */
    FollowLecturerBO selectFollowLecturerBO(@Param("id") String id);

    /**
     * 根据讲师UserId查询所有用户ID
     * @param userId 讲师UserId
     * @return 所有用户ID
     */
    List<String> selectUserIdListByLecturerId(@Param("userId") String userId);
}