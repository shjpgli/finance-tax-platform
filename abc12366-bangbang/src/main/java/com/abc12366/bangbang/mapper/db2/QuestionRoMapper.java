package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.Question;
import com.abc12366.bangbang.model.question.bo.QuestionAnswerBo;
import com.abc12366.bangbang.model.question.bo.QuestionBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * QuestionMapper数据库操作接口类
 * 
 **/

public interface QuestionRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Question  selectByPrimaryKey(@Param("id") String id);

    /**
     * 查询(根据查询条件查询)
     **/
    List<QuestionBo> selectList(Map<String, Object> map);

    /**
     * 查询邦友热议
     **/
    List<QuestionBo> selectListry(Map<String, Object> map);

}