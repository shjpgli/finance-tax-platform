package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumApply;
import com.abc12366.cms.model.curriculum.bo.CurriculumApplyBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumLecturerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumApplyBo> selectList(Map<String, Object> map);


}