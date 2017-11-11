package com.abc12366.uc.service.invoice;

import com.abc12366.uc.model.invoice.Invoice;
import com.abc12366.uc.model.invoice.InvoiceBack;
import com.abc12366.uc.model.invoice.bo.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 管理员开票、拒绝开票
     * @param invoiceCheckBO
     * @param request
     */
    void billing(InvoiceCheckBO invoiceCheckBO, HttpServletRequest request);

    List<InvoiceExpressExcel> selectInvoiceExpressExcelList(InvoiceBO invoice);

    void insertInvoiceExpressExcelList(List<InvoiceExpressExcel> expressExcelList, String expressCompId, HttpServletRequest request);

    void insertInvoicePrintExcelList(List<InvoiceExcel> invoiceList);

    /**
     * 确认收货
     */
    void confirmInvoice(Invoice invoice);

    /**
     * 自动确认收货
     */
    void automaticReceiptInvoice();

    /**
     * 状态为[待审批]发票申请数
     *
     * @return Integer
     */
    Integer selectTodoListCount();
}
