package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.KnowledgeVoteLog;
import org.apache.ibatis.annotations.Param;

/**
 *
 * KnowledgeVoteLogMapper数据库操作接口类
 *
 **/

public interface KnowledgeVoteLogMapper{


	/**
	 *
	 * 查询（根据主键ID查询）
	 *
	 **/
	KnowledgeVoteLog  selectByPrimaryKey ( @Param("id") Long id );

	/**
	 *
	 * 删除（根据主键ID删除）
	 *
	 **/
	int deleteByPrimaryKey ( @Param("id") Long id );

	/**
	 *
	 * 添加
	 *
	 **/
	int insert( KnowledgeVoteLog record );

	/**
	 *
	 * 添加 （匹配有值的字段）
	 *
	 **/
	int insertSelective( KnowledgeVoteLog record );

	/**
	 *
	 * 修改 （匹配有值的字段）
	 *
	 **/
	int updateByPrimaryKeySelective( KnowledgeVoteLog record );

	/**
	 *
	 * 修改（根据主键ID修改）
	 *
	 **/
	int updateByPrimaryKey ( KnowledgeVoteLog record );

}