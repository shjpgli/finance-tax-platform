package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.question.QuestionFactionMemberLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * QuestionFactionMemberLevelMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionMemberLevelMapper{

	/**
	 *
	 * 查询列表
	 *
	 **/
	List<QuestionFactionMemberLevel> selectList();

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionFactionMemberLevel selectByPrimaryKey(@Param("id") String id);

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
	int insert(QuestionFactionMemberLevel record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(QuestionFactionMemberLevel record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(QuestionFactionMemberLevel record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(QuestionFactionMemberLevel record);

}