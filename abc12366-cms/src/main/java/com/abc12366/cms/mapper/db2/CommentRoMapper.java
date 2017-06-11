package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Comment;
import com.abc12366.cms.model.bo.CommentBo;
import com.abc12366.cms.model.bo.CommentListBo;
import com.abc12366.cms.model.bo.CommentTjBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * CommentMapper数据库操作接口类
 * 
 **/

public interface CommentRoMapper {


	/**
	 * 
	 * 查询(根据主键ID查询)
	 * 
	 **/
	Comment selectByPrimaryKey(@Param("commentId") String commentId);

	/**
	 *
	 * 查询(根据查询条件查询)
	 *
	 **/
	List<CommentListBo> selectList(Map<String,Object> map);

	/**
	 *
	 * 查询(根据contentId查询)
	 *
	 **/
	List<CommentBo> selectBycontentId(@Param("contentId") String contentId);

	/**
	 *
	 * 查询(统计所有)
	 *
	 **/
	Long selectall();
	/**
	 *
	 * 查询(按天统计)
	 *
	 **/
	Long selectday();

	List<CommentTjBo> selectByday();

	List<CommentTjBo> selectByday0();

	List<CommentTjBo> selectByday1();

	/**
	 *
	 * 查询(按天统计)
	 *
	 **/
	Long selectweek();

	/**
	 *
	 * 查询(按月统计)
	 *
	 **/
	Long selectmonth();

	List<CommentTjBo> selectBymonth();

	List<CommentTjBo> selectBymonth0();

	List<CommentTjBo> selectBymonth1();

	/**
	 *
	 * 查询(按年统计)
	 *
	 **/
	Long selectyear();

	List<CommentTjBo> selectByyear();

	List<CommentTjBo> selectByyear0();

	List<CommentTjBo> selectByyear1();


}