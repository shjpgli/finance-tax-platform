package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumChapterCourseware;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumChapterCoursewareMapper数据库操作接口类
 * 
 **/

public interface CurriculumChapterCoursewareRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumChapterCourseware  selectByPrimaryKey(@Param("curriculumId") String curriculumId);


}