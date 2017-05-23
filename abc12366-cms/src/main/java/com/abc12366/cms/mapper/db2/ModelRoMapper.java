package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ContentType;
import com.abc12366.cms.model.Model;
import com.abc12366.cms.model.bo.ModelBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * ModelMapper数据库操作接口类
 * 
 **/

public interface ModelRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Model selectByPrimaryKey(@Param("modelId") String modelId);

	/**
	 *
	 * 查询(根据查询条件查询)
	 *
	 **/
	List<Model> selectList();


}