package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentTopic;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentTopicMapper数据库操作接口类
 * 
 **/

public interface ContentTopicRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentTopic  selectByPrimaryKey(@Param("id") Long id);


}