package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.ModelItem;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ModelItemMapper数据库操作接口类
 * 
 **/

public interface ModelItemMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("modelitemId") String modelitemId);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(ModelItem record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(ModelItem record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(ModelItem record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(ModelItem record);

}