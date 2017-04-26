package com.abc12366.admin.mapper.db1;

import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.Plug;

/**
 * 
 * PlugMapper数据库操作接口类
 * 
 **/

public interface PlugMapper{



	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey ( @Param("id") Long id );

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert( Plug record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Plug record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Plug record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Plug record );

}