package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumCollect;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumCollectMapper数据库操作接口类
 * 
 **/

public interface CurriculumCollectRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumCollect  selectByPrimaryKey(@Param("collectId") String collectId);


}