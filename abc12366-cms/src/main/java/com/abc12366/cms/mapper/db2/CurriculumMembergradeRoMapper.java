package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumMembergrade;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumMembergradeMapper数据库操作接口类
 * 
 **/

public interface CurriculumMembergradeRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumMembergrade  selectByPrimaryKey(@Param("curriculumId") String curriculumId);


}