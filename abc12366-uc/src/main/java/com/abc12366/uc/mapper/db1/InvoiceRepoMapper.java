package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.InvoiceRepo;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceRepoMapper数据库操作接口类
 * 
 **/

public interface InvoiceRepoMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(InvoiceRepo record);


	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(InvoiceRepo record);

}