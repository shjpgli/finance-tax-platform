package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ModelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * ModelItemMapper数据库操作接口类
 * 
 **/

public interface ModelItemRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ModelItem selectByPrimaryKey(@Param("id") Long id);

	/**
	 *
	 * 查询（根据modelId查询）
	 *
	 **/
	List<ModelItem> selectModeList(String modelId);


}