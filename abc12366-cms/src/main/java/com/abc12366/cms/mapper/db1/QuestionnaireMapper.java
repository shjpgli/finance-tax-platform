package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.questionnaire.Questionnaire;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionnaireMapper数据库操作接口类
 * 
 **/

public interface QuestionnaireMapper{



	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(Questionnaire record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(Questionnaire record);

    int delete(Questionnaire questionnaire);

    int updateAccessRate(Questionnaire questionnaire);

    int updateRecoveryRate(Questionnaire questionnaire);

	int updateSkinUrl(Questionnaire questionnaire);

    void updateStatus();
}