package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.Answer;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * AnswerMapper数据库操作接口类
 * 
 **/

public interface AnswerRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Answer  selectByPrimaryKey(@Param("id") String id);

}