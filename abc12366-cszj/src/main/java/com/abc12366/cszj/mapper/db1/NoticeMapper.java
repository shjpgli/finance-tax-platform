package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.bo.NoticeBO;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告管理
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:21 PM
 * @since 1.0.0
 */

public interface NoticeMapper {



	/**
	 * 
	 * 根据主键删除通知公告
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 新增通知公告
	 * 
	 **/
	int insert(NoticeBO notice);


	/**
	 * 
	 * 更新通知公告
	 * 
	 **/
	int update(NoticeBO notice);

	int updatecount(NoticeBO notice);

}