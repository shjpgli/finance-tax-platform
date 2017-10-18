package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.question.QuestionHeadman;
import com.abc12366.bangbang.model.question.bo.QuestionHeadmanBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * QuestionHeadmanMapper数据库操作接口类
 * 
 **/

public interface QuestionHeadmanMapper{

	/**
	 *
	 * 列表查询
	 *
	 **/
	List<QuestionHeadman> selectList();

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionHeadmanBo selectByPrimaryKey(@Param("id") String id);

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
	int insert(QuestionHeadman record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(QuestionHeadman record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(QuestionHeadman record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(QuestionHeadman record);

}