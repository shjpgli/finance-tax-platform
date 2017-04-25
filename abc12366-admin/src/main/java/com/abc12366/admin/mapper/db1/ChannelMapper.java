package com.abc12366.admin.mapper.db1;
import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.Channel;

/**
 * 
 * ChannelMapper数据库操作接口类
 * 
 **/

public interface ChannelMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Channel  selectByPrimaryKey ( @Param("id") Long id );

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
	int insert( Channel record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Channel record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Channel record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Channel record );

}