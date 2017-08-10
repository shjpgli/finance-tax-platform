package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.curriculum.CurriculumBrowse;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumBrowseMapper数据库操作接口类
 * 
 **/

public interface CurriculumBrowseMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("curriculumId") String curriculumId);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(CurriculumBrowse record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(CurriculumBrowse record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(CurriculumBrowse record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(CurriculumBrowse record);

}