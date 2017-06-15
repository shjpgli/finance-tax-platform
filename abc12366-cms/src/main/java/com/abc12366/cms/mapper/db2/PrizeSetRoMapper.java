package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.PrizeSet;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * PrizeSetMapper数据库操作接口类
 * 
 **/

public interface PrizeSetRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	PrizeSet selectByPrimaryKey(@Param("id") String id);


}