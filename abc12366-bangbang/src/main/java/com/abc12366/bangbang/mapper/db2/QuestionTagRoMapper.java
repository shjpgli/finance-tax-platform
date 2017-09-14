package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionTag;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionTagMapper数据库操作接口类
 * 
 **/

public interface QuestionTagRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionTag  selectByPrimaryKey(@Param("id") Long id);


}