package com.abc12366.uc.service.invoice;

import com.abc12366.uc.model.invoice.InvoiceRepo;
import com.abc12366.uc.model.invoice.bo.InvoiceRepoBO;
import com.abc12366.uc.model.invoice.bo.InvoiceUseCheckBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizhongwei
 * Created by MY on 2017-08-10.
 */
@Service
public interface InvoiceRepoService {

    List<InvoiceRepoBO> selectList(InvoiceRepoBO invoiceRepoBO);

    InvoiceRepoBO selectInvoiceRepo(InvoiceRepo invoiceRepo);

    void delete(String id);

    InvoiceRepoBO add(InvoiceRepoBO invoiceRepoBO);

    InvoiceRepoBO update(InvoiceRepoBO invoiceRepoBO);

}
