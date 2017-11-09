package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionAttention;
import com.abc12366.bangbang.model.question.bo.QcBangUserBo;
import com.abc12366.bangbang.model.question.bo.QcTitleBo;
import com.abc12366.bangbang.model.question.bo.QcUserBo;
import com.abc12366.bangbang.model.question.bo.QuestionAttentionBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * QuestionAttentionMapper数据库操作接口类
 * 
 **/

public interface QcUserRoMapper {

    /**
     *
     * 查询用户列表
     *
     **/
    List<QcBangUserBo> selectList();

    /**
     *
     * 查询我最近回答的问题
     *
     **/
    List<QcTitleBo> selectQuestionList(@Param("userId") String userId);



}