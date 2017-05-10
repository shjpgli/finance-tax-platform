package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ChannelAttr;
import com.abc12366.cms.model.bo.ChannelAttrBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * ChannelAttrMapper数据库操作接口类
 * 
 **/

public interface ChannelAttrRoMapper{


	/**
	 *
	 * 查询（根据主键ID查询）
	 *
	 **/
	ChannelAttrBo selectByPrimaryKey(@Param("channelId") String channelId);

	/**
	 *
	 * 查询（根据channelId查询）
	 *
	 **/
	List<ChannelAttrBo> selectByChannelId(@Param("channelId") String channelId);

}