package com.abc12366.uc.service.einvoice.impl;


import com.abc12366.uc.model.dzfp.*;
import com.abc12366.uc.service.einvoice.IEinvoiceService;
import com.abc12366.uc.webservice.DzfpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	@Override
	public Einvocie queryEinvoice(DzfqQueryReq dzfpGetReq) {
		Einvocie einvocie=null;
		try {
			einvocie = (Einvocie) DzfpClient.doSender("DFXJ1004", dzfpGetReq.tosendXml(), Einvocie.class);
		} catch (Exception e) {
			LOGGER.error("电子发票webservice调用异常,原因：",e);
		}
		return einvocie;
	}

	@Override
	public EinvocieDown queryEinvoice(DzfpDownloadReq downloadReq) {
		EinvocieDown einvocieDown=null;
		try {
			einvocieDown = (EinvocieDown) DzfpClient.doSender("DFXJ1005", downloadReq.tosendXml(), EinvocieDown.class);
		} catch (Exception e) {
			LOGGER.error("电子发票webservice调用异常,原因：",e);
		}
		return einvocieDown;
	}
	
}
