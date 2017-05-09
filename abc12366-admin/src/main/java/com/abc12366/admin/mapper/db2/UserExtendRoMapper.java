package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.UserExtend;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * UserExtendMapper数据库操作接口类
 * 
 **/

public interface UserExtendRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserExtend  selectById(@Param("id") String id);

	UserExtend selectUserExtendByUserId(String userId);

}