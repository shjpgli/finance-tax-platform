package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.EventApply;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * EventApplyMapper数据库操作接口类
 * 
 **/

public interface EventApplyMapper{

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("applyId") String applyId);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(EventApply record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(EventApply record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(EventApply record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(EventApply record);

}