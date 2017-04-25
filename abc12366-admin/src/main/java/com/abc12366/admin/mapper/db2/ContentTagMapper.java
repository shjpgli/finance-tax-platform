package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentTag;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContenttagMapper数据库操作接口类
 * 
 **/

public interface ContentTagMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentTag  selectByPrimaryKey(@Param("id") Long id);

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
	int insert(ContentTag record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(ContentTag record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(ContentTag record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(ContentTag record);

}