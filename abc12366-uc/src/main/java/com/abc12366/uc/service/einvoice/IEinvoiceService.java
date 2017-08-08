package com.abc12366.uc.service.einvoice;

import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.DzfqQueryReq;
import com.abc12366.uc.model.dzfp.Einvocie;

public interface IEinvoiceService {

	Einvocie getEinvoice(DzfpGetReq dzfpGetReq);

	Einvocie queryEinvoice(DzfqQueryReq dzfpGetReq);

}
