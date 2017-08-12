package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumEvaluate;
import com.abc12366.cms.model.curriculum.bo.CurriculumEvaluateBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumLecturerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CurriculumEvaluateMapper数据库操作接口类
 * 
 **/

public interface CurriculumEvaluateRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumEvaluate  selectByPrimaryKey(@Param("evaluateId") String evaluateId);

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumEvaluateBo> selectList(Map<String, Object> map);


}