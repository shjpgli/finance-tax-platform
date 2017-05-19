package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.InvoiceBack;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceBackMapper数据库操作接口类
 * 
 **/

public interface InvoiceBackMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int delete(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(InvoiceBack record);


	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(InvoiceBack record);

}