package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ContentTxt;
import com.abc12366.cms.model.bo.ContentTxtBo;
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
	ContentTxt selectByPrimaryKey(@Param("contentId") String contentId);

	/**
	 *
	 * 查询(根据主键ID查询)
	 *
	 **/
	ContentTxt selectByContentId(String contentId);



}