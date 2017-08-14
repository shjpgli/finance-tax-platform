package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.bo.InvoiceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InvoiceMapper数据库操作接口类
 **/

public interface InvoiceRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    InvoiceBO selectById(@Param("id") String id);


    List<InvoiceBO> selectList(InvoiceBO invoice);

    Invoice selectByInvoiceOrderNo(String invoiceOrderNo);

    Invoice selectAvailableInvoice();

    InvoiceBO selectUserInvoice(Invoice invoice);
}