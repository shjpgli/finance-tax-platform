package com.abc12366.admin.mapper.db1;

import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.ChannelAttr;

/**
 * 
 * ChannelAttrMapper数据库操作接口类
 * 
 **/

public interface ChannelAttrMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ChannelAttr  selectByPrimaryKey ( @Param("id") Long id );

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
	int insert( ChannelAttr record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( ChannelAttr record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( ChannelAttr record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( ChannelAttr record );

}