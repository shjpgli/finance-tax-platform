package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionAnswer;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
	QuestionAnswer  selectByPrimaryKey(@Param("id") String id);

    /**
     * 查询(根据查询条件查询)
     **/
    List<QuestionAnswerBo> selectList(Map<String, Object> map);

    /**
     * 查询(根据查询条件查询)
     **/
    List<QuestionAnswerBo> selectListByParentId(Map<String, Object> map);

    /**
     * 查询(根据查询条件查询)
     **/
    List<QuestionAnswerBo> selectListNew(Map<String, Object> map);

    /**
     * 查询(根据查询条件查询)
     **/
    String selectfactionId(Map<String, Object> map);

}