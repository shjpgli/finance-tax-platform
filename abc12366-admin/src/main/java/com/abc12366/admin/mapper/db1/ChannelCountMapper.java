package com.abc12366.admin.mapper.db1;
import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.ChannelCount;

/**
 * 
 * ChannelCountMapper数据库操作接口类
 * 
 **/

public interface ChannelCountMapper{


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
	int insert( ChannelCount record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( ChannelCount record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( ChannelCount record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( ChannelCount record );

}