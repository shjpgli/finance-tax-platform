package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.invoice.GoodsLog;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * GoodsLogMapper数据库操作接口类
 * 
 **/

public interface GoodsLogRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	GoodsLog  selectByPrimaryKey ( @Param("id") Long id );

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
	int insert( GoodsLog record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( GoodsLog record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( GoodsLog record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( GoodsLog record );

}