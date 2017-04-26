package com.abc12366.admin.mapper.db2;

import org.apache.ibatis.annotations.Param;

import com.abc12366.admin.model.cms.ChannelModel;

/**
 * 
 * ChannelModelMapper数据库操作接口类
 * 
 **/

public interface ChannelModelRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ChannelModel  selectByPrimaryKey ( @Param("id") Long id );


}