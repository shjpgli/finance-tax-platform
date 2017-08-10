package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumChapter;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumChapterMapper数据库操作接口类
 * 
 **/

public interface CurriculumChapterRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumChapter  selectByPrimaryKey(@Param("chapterId") String chapterId);


}