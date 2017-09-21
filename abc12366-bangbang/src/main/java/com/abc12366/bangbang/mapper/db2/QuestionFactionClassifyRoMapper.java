package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionFactionClassify;
import com.abc12366.bangbang.model.question.QuestionFactionTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * QuestionFactionClassifyMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionClassifyRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFactionClassify selectByPrimaryKey(@Param("id") String id);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionFactionClassify> selectList(@Param("factionId") String factionId);


}