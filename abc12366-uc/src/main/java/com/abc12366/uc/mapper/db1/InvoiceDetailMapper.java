package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.InvoiceDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceDetailMapper数据库操作接口类
 * 
 **/

public interface InvoiceDetailMapper{



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
	int insert(InvoiceDetail record);


	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(InvoiceDetail record);

}