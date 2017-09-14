package com.abc12366.uc.service;

import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.InvoiceBack;
import com.abc12366.uc.model.bo.*;
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
     */
    List<InvoiceExcel> selectInvoicePrintExcelList(InvoiceBO invoice);

    InvoiceBackBO refund(InvoiceBackBO invoiceBackBO);

    InvoiceBackBO refundCheck(InvoiceBack invoiceBack);

    List<InvoiceBackBO> selectBOList(InvoiceBackBO invoiceBackBO);

    InvoiceBackBO selectBackOne(String id);

    InvoiceBO selectUserInvoice(Invoice invoice);

    void billing(InvoiceCheckBO invoiceCheckBO);

    List<InvoiceExpressExcel> selectInvoiceExpressExcelList(InvoiceBO invoice);

    void insertInvoiceExpressExcelList(List<InvoiceExpressExcel> expressExcelList, String expressCompId);

    void insertInvoicePrintExcelList(List<InvoiceExcel> invoiceList);

    /**
     * 确认收货
     */
    void confirmInvoice(Invoice invoice);

}
