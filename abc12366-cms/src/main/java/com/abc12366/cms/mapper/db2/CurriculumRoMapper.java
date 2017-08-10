package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.curriculum.Curriculum;
import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;
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


}