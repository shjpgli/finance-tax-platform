package com.abc12366.uc.service.einvoice.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.service.einvoice.IEinvoiceService;
import com.abc12366.uc.webservice.DzfpClient;

@Service
public class EinvoiceServiceImpl implements IEinvoiceService {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(EinvoiceServiceImpl.class);
	
	@Override
	public Einvocie getEinvoice(DzfpGetReq dzfpGetReq) {
		Einvocie einvocie=null;
		try {
			einvocie = (Einvocie) DzfpClient.doSender("DFXJ1001", dzfpGetReq.tosendXml(), Einvocie.class);
		} catch (Exception e) {
			LOGGER.error("电子发票webservice调用异常,原因：",e);
		}
		return einvocie;
	}
	
}
