package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.KnowledgeTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * KnowledgeTagMapper数据库操作接口类
 * 
 **/

public interface KnowledgeTagMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	KnowledgeTag  selectByPrimaryKey(@Param("id") String id);

	/**
	 *
	 * 查询列表
	 *
	 **/
	List<KnowledgeTag> selectList(@Param("keywords") String keywords);

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
	int insert(KnowledgeTag record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(KnowledgeTag record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(KnowledgeTag record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(KnowledgeTag record);

}