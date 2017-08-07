package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KnowledgeBaseMapper数据库操作接口类
 **/

public interface KnowledgeBaseMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    KnowledgeBase selectByPrimaryKey(@Param("id") String id);


    /*
    *
    * 知识详情页 感兴趣的知识查询
    *
    **/
    List<KnowledgeBase> interestedList(@Param("id") String id, @Param("num") int num);


    /*
    *
    * 首页热点推荐查询
    *
    **/
    List<KnowledgeBase> hotList(KnowledgeBaseHotParamBO param);

    /*
    *
    * 列表查询
    *
    **/
    List<KnowledgeBase> selectList(KnowledgeBaseParamBO param);


    /**
     * 删除（根据主键ID删除）
     **/
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 删除（根据主键ID批量删除）
     **/
    int deleteByPrimaryKeys(List<String> ids);


    /**
     * 添加
     **/
    int insert(KnowledgeBase record);

    /**
     * 添加 （匹配有值的字段）
     **/
    int insertSelective(KnowledgeBase record);

    /**
     * 修改 （匹配有值的字段）
     **/
    int updateByPrimaryKeySelective(KnowledgeBase record);

    /**
     * 修改（根据主键ID修改）
     **/
    int updateByPrimaryKey(KnowledgeBase record);

    /**
     * 新增知识的有用投票数
     **/
    int addUsefulVoteByPK(String id);

    /**
     * 新增知识的无用投票数
     **/
    int addUselessVoteByPK(String id);

    /**
     * 新增知识的浏览量
     **/
    int addPVByPK(String id);

}