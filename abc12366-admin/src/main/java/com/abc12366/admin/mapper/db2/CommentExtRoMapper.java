package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.CommentExt;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CommentExtMapper数据库操作接口类
 * 
 **/

public interface CommentExtRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	CommentExt  selectByPrimaryKey(@Param("id") Long id);


}