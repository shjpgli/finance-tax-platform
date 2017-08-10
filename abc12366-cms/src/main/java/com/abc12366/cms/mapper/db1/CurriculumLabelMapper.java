package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.curriculum.CurriculumLabel;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumLabelMapper数据库操作接口类
 * 
 **/

public interface CurriculumLabelMapper{


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
	int insert(CurriculumLabel record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(CurriculumLabel record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(CurriculumLabel record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(CurriculumLabel record);

}