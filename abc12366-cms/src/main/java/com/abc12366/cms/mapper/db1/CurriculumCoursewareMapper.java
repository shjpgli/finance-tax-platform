package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.curriculum.CurriculumCourseware;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumCoursewareMapper数据库操作接口类
 * 
 **/

public interface CurriculumCoursewareMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("coursewareId") String coursewareId);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(CurriculumCourseware record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(CurriculumCourseware record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(CurriculumCourseware record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(CurriculumCourseware record);

}