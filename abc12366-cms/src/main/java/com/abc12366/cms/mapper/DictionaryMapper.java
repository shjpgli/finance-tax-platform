package com.abc12366.cms.mapper;

import org.apache.ibatis.annotations.Param;

import com.abc12366.cms.model.Dictionary;

/**
 * 
 * DictionaryMapper数据库操作接口类
 * 
 **/

public interface DictionaryMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Dictionary  selectByPrimaryKey ( @Param("id") Long id );

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
	int insert( Dictionary record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Dictionary record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Dictionary record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Dictionary record );

}