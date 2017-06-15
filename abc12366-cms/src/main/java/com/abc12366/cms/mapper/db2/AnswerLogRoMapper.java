package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.AnswerLog;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * AnswerLogMapper数据库操作接口类
 * 
 **/

public interface AnswerLogRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	AnswerLog selectByPrimaryKey(@Param("id") String id);

}