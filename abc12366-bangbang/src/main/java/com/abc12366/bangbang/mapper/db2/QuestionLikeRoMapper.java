package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.question.QuestionLike;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionLikeMapper数据库操作接口类
 * 
 **/

public interface QuestionLikeRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	QuestionLike  selectByPrimaryKey(@Param("id") Long id);


}