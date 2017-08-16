package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.curriculum.CurriculumCollect;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumCollectBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询(根据查询条件查询)
     **/
    List<CurriculumCollectBo> selectList(Map<String, Object> map);


}