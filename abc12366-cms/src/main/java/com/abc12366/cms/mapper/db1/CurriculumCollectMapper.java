package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.curriculum.CurriculumCollect;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumCollectMapper数据库操作接口类
 * 
 **/

public interface CurriculumCollectMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("collectId") String collectId);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(CurriculumCollect record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(CurriculumCollect record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(CurriculumCollect record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(CurriculumCollect record);

}