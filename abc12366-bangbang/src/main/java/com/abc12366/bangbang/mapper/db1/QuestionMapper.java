package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.question.Question;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionMapper数据库操作接口类
 * 
 **/

public interface QuestionMapper{


	/**
	 *
	 * 删除（根据主键ID删除）
	 *
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(Question record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(Question record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(Question record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(Question record);

	/**
	 *
	 * 清空推荐截止时间（根据主键IDS修改）
	 *
	 **/
	int clearRecommendEndTimeByPks(String ids);

    /**
     * 修改浏览量(根据主键ID)
     **/
    int updateBrowseNum(@Param("id") String id);


}