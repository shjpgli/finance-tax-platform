package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.Organization;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * OrganizationMapper数据库操作接口类
 * 
 **/

public interface OrganizationMapper{


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
	int insert(Organization record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(Organization record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(Organization record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(Organization record);

}