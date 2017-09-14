package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionCollect;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionCollectMapper数据库操作接口类
 * 
 **/

public interface QuestionCollectRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionCollect  selectByPrimaryKey(@Param("id") Long id);


}