package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.bo.AdPageBO;
import org.apache.ibatis.annotations.Param;
/**
 * 广告图片管理
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:21 PM
 * @since 1.0.0
 */

public interface AdPageMapper {



	/**
	 * 
	 * 根据主键删除广告图片
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 新增广告图片
	 * 
	 **/
	int insert(AdPageBO adPage);


	/**
	 * 
	 * 更新广告图片
	 * 
	 **/
	int update(AdPageBO adPage);

}