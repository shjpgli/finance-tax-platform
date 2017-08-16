package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.curriculum.CurriculumLecturer;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumLecturerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumLecturerBo> selectList(Map<String, Object> map);


}