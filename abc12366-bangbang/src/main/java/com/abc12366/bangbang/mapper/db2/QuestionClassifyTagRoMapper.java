package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionClassifyTag;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionClassifyTagMapper数据库操作接口类
 * 
 **/

public interface QuestionClassifyTagRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionClassifyTag  selectByPrimaryKey(@Param("id") Long id);


}