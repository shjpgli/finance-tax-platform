package com.abc12366.uc.service;

import com.abc12366.uc.model.InvoiceDetail;
import com.abc12366.uc.model.InvoiceRepo;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.model.bo.InvoiceRepoBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MY on 2017-06-08.
 */
@Service
public interface InvoiceRepoService {

    List<InvoiceRepo> selectInvoiceRepoList(InvoiceRepo invoiceRepo);

    InvoiceRepoBO addInvoiceRepo(InvoiceRepoBO invoiceRepoBO);

    void deleteInvoiceRepo(String id);

    List<InvoiceDetail> selectInvoiceDetailList(InvoiceDetail invoiceDetail);

    void deleteInvoiceDetail(String id);

    void invalidInvoiceDetail(String id);

    InvoiceDetail selectInvoiceDetail();

    List<InvoiceDetail> selectInvoiceDetailListByInvoice(InvoiceDetail invoiceDetail);
}
