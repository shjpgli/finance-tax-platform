package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.questionnaire.AccessLog;
import com.abc12366.cms.model.questionnaire.bo.AccessLogBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * AccessLogMapper数据库操作接口类
 * 
 **/

public interface AccessLogRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	AccessLog selectByPrimaryKey ( @Param("id") String id );

	List<AccessLogBO> selectList(AccessLog accessLog);

	List<AccessLogBO> selectAccessLogStatis(AccessLog accessLog);
}