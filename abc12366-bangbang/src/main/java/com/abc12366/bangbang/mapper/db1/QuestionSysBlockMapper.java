package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.question.QuestionSysBlock;
import com.abc12366.bangbang.model.question.bo.QuestionSysBlockBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * QuestionSysBlockMapper数据库操作接口类
 * 
 **/

public interface QuestionSysBlockMapper{

	/**
	 *
	 * 列表查询
	 *
	 **/
	List<QuestionSysBlockBo> selectList();


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionSysBlock selectByPrimaryKey(@Param("id") String id);

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
	int insert(QuestionSysBlock record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(QuestionSysBlock record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(QuestionSysBlock record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(QuestionSysBlock record);

}