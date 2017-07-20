package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.LoginInfo;
import com.abc12366.admin.model.bo.LoginInfoBO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * LoginInfoMapper数据库操作接口类
 * 
 **/

public interface LoginInfoRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	LoginInfo  selectByPrimaryKey(@Param("id") String id);


    LoginInfo selectOne(LoginInfo loginInfo);

    LoginInfo selectInfoByToken(LoginInfo loginInfo);

    LoginInfoBO selectLoginInfoByToken(String token);
}