package com.abc12366.gateway.mapper.db1;

import com.abc12366.gateway.model.IpSetting;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * IpSettingMapper数据库操作接口类
 * 
 **/

public interface IpSettingMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	IpSetting selectByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(IpSetting record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(IpSetting record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(IpSetting record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(IpSetting record);

}