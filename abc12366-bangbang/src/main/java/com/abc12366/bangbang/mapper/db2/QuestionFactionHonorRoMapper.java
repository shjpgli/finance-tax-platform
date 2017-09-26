package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionFactionHonor;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionFactionHonorMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionHonorRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFactionHonor selectByPrimaryKey(@Param("id") String id);


}