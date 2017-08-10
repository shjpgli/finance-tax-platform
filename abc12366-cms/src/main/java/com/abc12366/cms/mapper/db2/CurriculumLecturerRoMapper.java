package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumLecturer;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumLecturerMapper数据库操作接口类
 * 
 **/

public interface CurriculumLecturerRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumLecturer  selectByPrimaryKey(@Param("lecturerId") String lecturerId);


}