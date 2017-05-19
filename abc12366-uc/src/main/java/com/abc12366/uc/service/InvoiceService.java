package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.InvoiceBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface InvoiceService {

    List<InvoiceBO> selectList(InvoiceBO product);

    InvoiceBO selectOne(String id);

    InvoiceBO updateInvoice(InvoiceBO productBO);

    int deleteByIdAndUserId(InvoiceBO orderBO);

    InvoiceBO addInvoice(InvoiceBO orderBO);
}
