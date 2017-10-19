package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionTipOff;
import com.abc12366.bangbang.model.question.bo.QuestionTipOffBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author liuQi
 * @Date 2017/10/18 11:12
 */
public interface QuestionTipOffRoMapper {

    /**
     *
     * 列表查询
     *
     **/
    List<QuestionTipOffBo> selectList();


    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    QuestionTipOff selectByPrimaryKey(@Param("id") String id);

    int selectExist(Map map);

    int selectTipoffCnt(@Param("sourceId") String sourceId);

}
