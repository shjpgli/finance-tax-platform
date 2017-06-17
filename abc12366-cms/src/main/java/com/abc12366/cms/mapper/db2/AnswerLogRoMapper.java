package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.AnswerLog;
import com.abc12366.cms.model.questionnaire.bo.AnswerLogBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<AnswerLogBO> selectList(AnswerLog answerLog);

    AnswerLogBO selectOne(String id);

    AnswerLogBO selectAvgTime(AnswerLog answerLog);
}