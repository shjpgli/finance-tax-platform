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
    QuestionAnswerBo  selectByPrimaryKey(@Param("id") String id);

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

    /**
     * 查询我的回答
     **/
    List<QuestionAnswerBo> selectMyAnswerList(Map<String, Object> map);

    /**
     * 查询我的评论
     **/
    List<QuestionAnswerBo> selectMyCommentList(Map<String, Object> map);

    /**
     *
     * 查询回复数
     *
     **/
    int  selectAnswerCnt(@Param("questionId") String questionId);

    /**
     *
     * 查询评论数
     *
     **/
    int  selectCommentCnt(@Param("id") String id);

}