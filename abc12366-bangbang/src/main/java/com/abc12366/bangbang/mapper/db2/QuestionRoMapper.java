package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.Question;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionMapper数据库操作接口类
 * 
 **/

public interface QuestionRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Question  selectByPrimaryKey(@Param("id") Long id);

}