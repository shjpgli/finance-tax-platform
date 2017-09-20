package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionFaction;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionTjBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * QuestionFactionMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFaction selectByPrimaryKey(@Param("factionId") String factionId);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionFactionBo> selectList(Map<String, Object> map);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    QuestionFactionTjBo selectFactionTj(@Param("factionId") String factionId);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionAnswerBo> selectdtListByFactionId(@Param("factionId") String factionId);


}