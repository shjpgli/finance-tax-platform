package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Content;
import com.abc12366.cms.model.bo.ContentListBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * ContentMapper数据库操作接口类
 * 
 **/

public interface ContentRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Content selectByPrimaryKey(@Param("contentId") String contentId);

	/**
	 *
	 * 查询(根据查询条件查询)
	 *
	 **/
	List<ContentListBo> selectList(Map<String,Object> map);

}