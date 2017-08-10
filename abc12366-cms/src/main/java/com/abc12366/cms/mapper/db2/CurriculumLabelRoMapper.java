package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumLabel;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumLabelMapper数据库操作接口类
 * 
 **/

public interface CurriculumLabelRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumLabel  selectByPrimaryKey(@Param("curriculumId") String curriculumId);


}