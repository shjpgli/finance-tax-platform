package com.abc12366.uc.service.einvoice;

import com.abc12366.uc.model.dzfp.*;

public interface IEinvoiceService {

	Einvocie getEinvoice(DzfpGetReq dzfpGetReq);

	Einvocie queryEinvoice(DzfqQueryReq dzfpGetReq);

	EinvocieDown queryEinvoice(DzfpDownloadReq downloadReq);

}
