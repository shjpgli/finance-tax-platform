package com.abc12366.admin.mapper.db2;

import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.Topic;

/**
 * 
 * TopicMapper数据库操作接口类
 * 
 **/

public interface TopicRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Topic  selectByPrimaryKey ( @Param("id") Long id );

}