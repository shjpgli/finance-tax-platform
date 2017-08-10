package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.curriculum.CurriculumOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * CurriculumOrderMapper数据库操作接口类
 * 
 **/

public interface CurriculumOrderRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CurriculumOrder  selectByPrimaryKey(@Param("orderId") String orderId);


}