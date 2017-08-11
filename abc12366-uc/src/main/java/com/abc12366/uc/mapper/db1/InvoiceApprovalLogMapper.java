package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.invoice.InvoiceApprovalLog;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceApprovalLogMapper数据库操作接口类
 * 
 **/

public interface InvoiceApprovalLogMapper{


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
	int insert(InvoiceApprovalLog record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int update(InvoiceApprovalLog record);

	void deleteByUseId(String id);
}