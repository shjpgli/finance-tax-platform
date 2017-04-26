package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CommentMapper数据库操作接口类
 * 
 **/

public interface CommentMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Comment selectByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 删除(根据主键ID删除)
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(Comment record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(Comment record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(Comment record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(Comment record);

}