package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.bo.AdPageBO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * AnswerMapper数据库操作接口类
 * 
 **/

public interface AdPageMapper {



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
	int insert(AdPageBO adPage);


	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(AdPageBO adPage);

}