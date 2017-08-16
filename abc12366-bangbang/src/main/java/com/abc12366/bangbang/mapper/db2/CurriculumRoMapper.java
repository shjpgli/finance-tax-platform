package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.curriculum.Curriculum;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumListBo;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumListsyBo;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumSituationBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CurriculumMapper数据库操作接口类
 * 
 **/

public interface CurriculumRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Curriculum  selectByPrimaryKey(@Param("curriculumId") String curriculumId);

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumListBo> selectList(Map<String, Object> map);

    /**
     * 查询最新课程
     **/
    List<CurriculumListsyBo> selectListNew(Map<String, Object> map);

    /**
     * 查询推荐课程
     **/
    List<CurriculumListBo> selectRecommend();

    /**
     * 查询课程授课信息
     **/
    CurriculumSituationBo selectSituation(@Param("curriculumId") String curriculumId);


}