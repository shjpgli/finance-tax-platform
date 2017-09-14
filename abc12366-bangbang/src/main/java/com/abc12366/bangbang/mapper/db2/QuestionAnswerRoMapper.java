package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionAnswer;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionAnswerMapper数据库操作接口类
 * 
 **/

public interface QuestionAnswerRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionAnswer  selectByPrimaryKey(@Param("id") Long id);

}