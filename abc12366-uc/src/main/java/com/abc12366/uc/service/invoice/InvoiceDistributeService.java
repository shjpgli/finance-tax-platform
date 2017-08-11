package com.abc12366.uc.service.invoice;

import com.abc12366.uc.model.invoice.InvoiceUseApply;
import com.abc12366.uc.model.invoice.bo.InvoiceUseApplyBO;
import com.abc12366.uc.model.invoice.bo.InvoiceUseCheckBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MY on 2017-08-09.
 */
@Service
public interface InvoiceDistributeService {

    List<InvoiceUseApplyBO> selectList(InvoiceUseApplyBO applyBO);

    InvoiceUseApplyBO selectInvoiceUseApply(InvoiceUseApply invoiceUseApply);

    void delete(String id);

    InvoiceUseApplyBO add(InvoiceUseApplyBO invoiceUseApplyBO);

    InvoiceUseApplyBO update(InvoiceUseApplyBO invoiceUseApplyBO);

    void checkUseApplay(InvoiceUseCheckBO invoiceUseCheckBO);

    void distributeUseApply(InvoiceUseCheckBO invoiceUseCheckBO);
}
