package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumChapter;
import com.abc12366.cms.model.curriculum.CurriculumClassify;
import com.abc12366.cms.model.curriculum.bo.CurriculumChapterBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumClassifyBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CurriculumChapterMapper数据库操作接口类
 * 
 **/

public interface CurriculumClassifyRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
    CurriculumClassify selectByPrimaryKey(@Param("classifyId") String classifyId);

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumClassifyBo> selectList(Map<String, Object> map);


}