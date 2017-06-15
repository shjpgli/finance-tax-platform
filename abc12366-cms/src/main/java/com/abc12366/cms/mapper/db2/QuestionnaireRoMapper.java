package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.Questionnaire;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionnaireMapper数据库操作接口类
 * 
 **/

public interface QuestionnaireRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Questionnaire  selectByPrimaryKey(@Param("id") String id);


}