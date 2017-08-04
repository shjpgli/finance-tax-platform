package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.KnowledgeTagRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * KnowledgeTagRelMapper数据库操作接口类
 * 
 **/

public interface KnowledgeTagRelMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	KnowledgeTagRel  selectByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);


	/**
	 *
	 * 删除（根据KnowledgeID删除）
	 *
	 **/
	int deleteByKnowledgeId(@Param("KnowledgeId") String KnowledgeId);

	/**
	 *
	 * 删除（根据KnowledgeIDs批量删除）
	 *
	 **/
	int deleteByKnowledgeIds(List<String> ids);
	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(KnowledgeTagRel record);

	/**
	 *
	 * 批量添加
	 *
	 **/
	int insertBatch(List<KnowledgeTagRel> list);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(KnowledgeTagRel record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(KnowledgeTagRel record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(KnowledgeTagRel record);

}