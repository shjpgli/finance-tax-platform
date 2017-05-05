package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * OrganizationMapper数据库操作接口类
 * 
 **/

public interface OrganizationRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Organization  selectOrganizationById(@Param("id") String id);

    List<Organization> selectList();
}