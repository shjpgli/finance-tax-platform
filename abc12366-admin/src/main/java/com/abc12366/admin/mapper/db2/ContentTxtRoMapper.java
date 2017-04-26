package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentTxt;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentTxtMapper数据库操作接口类
 * 
 **/

public interface ContentTxtRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentTxt  selectByPrimaryKey(@Param("id") Long id);

}