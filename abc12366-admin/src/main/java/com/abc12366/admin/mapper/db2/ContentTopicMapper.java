package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentTopic;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentTopicMapper数据库操作接口类
 * 
 **/

public interface ContentTopicMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentTopic  selectByPrimaryKey(@Param("id") Long id);

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
	int insert(ContentTopic record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(ContentTopic record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(ContentTopic record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(ContentTopic record);

}