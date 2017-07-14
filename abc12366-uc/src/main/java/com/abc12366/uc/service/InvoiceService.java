package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceBackBO;
import com.abc12366.uc.model.bo.InvoiceExcel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
@Service
public interface InvoiceService {

    List<InvoiceBO> selectList(InvoiceBO invoice);

    InvoiceBO selectOne(String id);

    InvoiceBO updateInvoice(InvoiceBO invoiceBO);

    int deleteByIdAndUserId(InvoiceBO invoiceBO);

    InvoiceBO addInvoice(InvoiceBO invoiceBO);

    /**
     * 发票信息导出
     *
     * @param invoice
     * @return
     */
    List<InvoiceExcel> selectInvoiceExcelList(InvoiceBO invoice);

    InvoiceBackBO refund(InvoiceBackBO invoiceBackBO);

    InvoiceBackBO refundCheck(InvoiceBackBO invoiceBackBO);

    List<InvoiceBackBO> selectBOList(InvoiceBackBO invoiceBackBO);

    InvoiceBackBO selectBackOne(String id);
}
