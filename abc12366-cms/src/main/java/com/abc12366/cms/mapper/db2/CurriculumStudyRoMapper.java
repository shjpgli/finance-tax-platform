package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumStudy;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumStudyMapper数据库操作接口类
 * 
 **/

public interface CurriculumStudyRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumStudy  selectByPrimaryKey(@Param("curriculumId") String curriculumId);

}