package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CommentMapper数据库操作接口类
 * 
 **/

public interface CommentRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Comment selectByPrimaryKey(@Param("id") Long id);


}