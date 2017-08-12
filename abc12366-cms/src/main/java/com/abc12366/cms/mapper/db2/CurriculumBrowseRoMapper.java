package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumBrowse;
import com.abc12366.cms.model.curriculum.bo.CurriculumBrowseBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumLecturerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CurriculumBrowseMapper数据库操作接口类
 * 
 **/

public interface CurriculumBrowseRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumBrowse  selectByPrimaryKey(@Param("browseId") String browseId);

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumBrowseBo> selectList(Map<String, Object> map);


}