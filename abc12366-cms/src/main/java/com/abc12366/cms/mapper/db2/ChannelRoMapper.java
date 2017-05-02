package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Channel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * ChannelMapper数据库操作接口类
 * 
 **/

public interface ChannelRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Channel selectByPrimaryKey(@Param("id") Long id);

	List<Channel> selectListByParam(Channel channel);
}