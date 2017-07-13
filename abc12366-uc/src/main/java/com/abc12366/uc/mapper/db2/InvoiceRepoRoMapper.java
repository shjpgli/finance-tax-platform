package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.InvoiceRepo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * InvoiceRepoMapper数据库操作接口类
 * 
 **/

public interface InvoiceRepoRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	InvoiceRepo  selectByPrimaryKey(@Param("id") String id);

    List<InvoiceRepo> selectInvoiceRepoList(InvoiceRepo invoiceRepo);
}