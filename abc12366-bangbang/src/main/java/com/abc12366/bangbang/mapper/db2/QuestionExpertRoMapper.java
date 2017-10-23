package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionExpert;
import com.abc12366.bangbang.model.question.bo.QuestionExpertBO;
import com.abc12366.bangbang.model.question.bo.QuestionExpertParamBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/19 17:25
 */
public interface QuestionExpertRoMapper {

    /**
     *
     * 列表查询
     *
     **/
    List<QuestionExpertBO> selectList(QuestionExpertParamBo param);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    QuestionExpertBO selectByPrimaryKey(@Param("id") String id);

}
