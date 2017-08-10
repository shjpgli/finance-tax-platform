package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumCourseware;
import com.abc12366.cms.model.curriculum.bo.CurriculumCoursewareBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;
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