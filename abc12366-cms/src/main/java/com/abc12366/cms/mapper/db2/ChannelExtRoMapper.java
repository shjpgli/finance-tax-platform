package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.bo.ChannelExtBo;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ChannelExtMapper数据库操作接口类
 * 
 **/

public interface ChannelExtRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ChannelExtBo selectByPrimaryKey(@Param("channelId") String channelId);


}