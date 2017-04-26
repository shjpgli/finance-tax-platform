package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.cms.ContentAttr;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentAttrMapper数据库操作接口类
 * 
 **/

public interface ContentAttrMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentAttr  selectByPrimaryKey(@Param("id") Long id);

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
	int insert(ContentAttr record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(ContentAttr record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(ContentAttr record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(ContentAttr record);

}