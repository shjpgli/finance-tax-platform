package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.CommentExt;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CommentExtMapper数据库操作接口类
 * 
 **/

public interface CommentExtMapper{


	/**
	 * 
	 * 删除(根据主键ID删除)
	 * 
	 **/
	int deleteByPrimaryKey(@Param("commentId") String commentId);

	/**
	 *
	 * 删除(根据主键ID批量删除)
	 *
	 **/
	int deleteList(@Param("commentIds") String[] commentIds);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(CommentExt record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(CommentExt record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(CommentExt record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(CommentExt record);

}