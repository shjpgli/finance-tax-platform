package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentChannel;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentChannelMapper数据库操作接口类
 * 
 **/

public interface ContentChannelRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentChannel  selectByPrimaryKey(@Param("id") Long id);


}