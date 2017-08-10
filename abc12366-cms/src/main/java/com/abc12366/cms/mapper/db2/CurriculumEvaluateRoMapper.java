package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumEvaluate;
import org.apache.ibatis.annotations.Param;

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


}