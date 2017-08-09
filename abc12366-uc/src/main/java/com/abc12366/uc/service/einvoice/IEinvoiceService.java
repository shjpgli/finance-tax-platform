package com.abc12366.uc.service.einvoice;

import com.abc12366.uc.model.dzfp.DzfpDownloadReq;
import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.DzfqQueryReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.model.dzfp.EinvocieDown;

public interface IEinvoiceService {

	Einvocie getEinvoice(DzfpGetReq dzfpGetReq);

	Einvocie queryEinvoice(DzfqQueryReq dzfpGetReq);

	EinvocieDown queryEinvoice(DzfpDownloadReq downloadReq);

}
