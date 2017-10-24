package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionFactionAllocation;
import com.abc12366.bangbang.model.question.bo.QuestionFactionAllocationBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * QuestionFactionAllocationMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionAllocationRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFactionAllocation selectByPrimaryKey(@Param("id") String id);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionFactionAllocationBo> selectList(Map map);


}