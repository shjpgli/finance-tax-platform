package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.curriculum.CurriculumCourseware;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumCoursewareBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CurriculumCoursewareMapper数据库操作接口类
 * 
 **/

public interface CurriculumCoursewareRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumCourseware  selectByPrimaryKey(@Param("coursewareId") String coursewareId);

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumCoursewareBo> selectList(Map<String, Object> map);


}