package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.InvoiceDetail;
import com.abc12366.uc.model.InvoiceRepo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * InvoiceDetailMapper数据库操作接口类
 * 
 **/

public interface InvoiceDetailRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	InvoiceDetail  selectByPrimaryKey(@Param("id") String id);


	List<InvoiceDetail> selectInvoiceDetailList(InvoiceDetail invoiceDetail);

	InvoiceRepo selectByInvoiceNo(String invoiceNo);

	InvoiceRepo selectInvoiceRepo(String status);

	List<InvoiceDetail> selectByIdAndStatus(String id);
}