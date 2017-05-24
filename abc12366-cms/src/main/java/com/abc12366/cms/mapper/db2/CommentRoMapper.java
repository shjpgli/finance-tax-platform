package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Comment;
import com.abc12366.cms.model.bo.CommentBo;
import com.abc12366.cms.model.bo.CommentListBo;
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


}