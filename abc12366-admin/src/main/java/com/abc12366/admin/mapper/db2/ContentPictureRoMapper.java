package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.cms.ContentPicture;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ContentPictureMapper数据库操作接口类
 * 
 **/

public interface ContentPictureRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	ContentPicture  selectByPrimaryKey(@Param("id") Long id);

}