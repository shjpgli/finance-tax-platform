package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.bo.InvoiceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * InvoiceMapper数据库操作接口类
 * 
 **/

public interface InvoiceRoMapper {


	/**
	 *
	 * 查询（根据主键ID查询）
	 *
	 **/
	InvoiceBO  selectById(@Param("id") String id);


    List<InvoiceBO> selectList(InvoiceBO invoice);

    Invoice selectByUserOrderNo(String userOrderNo);

    Invoice selectAvailableInvoice();

    InvoiceBO selectUserInvoice(Invoice invoice);
}