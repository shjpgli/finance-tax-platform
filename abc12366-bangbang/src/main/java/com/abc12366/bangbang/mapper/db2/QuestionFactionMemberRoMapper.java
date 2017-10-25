package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionFactionMember;
import com.abc12366.bangbang.model.question.QuestionMemberHonor;
import com.abc12366.bangbang.model.question.bo.QuestionFactionMemberBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * QuestionFactionMemberMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionMemberRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFactionMember selectByPrimaryKey(@Param("memberId") String memberId);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionFactionMemberBo> selectList(Map<String, Object> map);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionFactionMemberBo> selectListTj(Map<String, Object> map);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    int selectSplendidNum(Map<String, Object> map);

    /**
     *
     * 查询用户是否已申请
     *
     **/
    int selectMemberCnt(Map<String, Object> map);

    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    List<QuestionMemberHonor> selectMemberHonorList(@Param("factionId") String factionId);


}