package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumBrowse;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumBrowseMapper数据库操作接口类
 * 
 **/

public interface CurriculumBrowseRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumBrowse  selectByPrimaryKey(@Param("curriculumId") String curriculumId);


}