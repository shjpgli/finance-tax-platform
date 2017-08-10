package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumApply;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumApplyMapper数据库操作接口类
 * 
 **/

public interface CurriculumApplyRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumApply  selectByPrimaryKey(@Param("applyId") String applyId);


}