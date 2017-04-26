package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.cms.ContentPicture;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentPictureMapper数据库操作接口类
 * 
 **/

public interface ContentPictureMapper{


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentPicture  selectByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 删除(根据主键ID删除)
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(ContentPicture record);

	/**
	 * 
	 * 添加(匹配有值的字段)
	 * 
	 **/
	int insertSelective(ContentPicture record);

	/**
	 * 
	 * 修改(匹配有值的字段)
	 * 
	 **/
	int updateByPrimaryKeySelective(ContentPicture record);

	/**
	 * 
	 * 修改(根据主键ID修改)
	 * 
	 **/
	int updateByPrimaryKey(ContentPicture record);

}