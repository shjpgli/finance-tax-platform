package com.abc12366.uc.web.einvoice;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.dzfp.DzfpDownloadReq;
import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.DzfqQueryReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.model.dzfp.EinvocieDown;
import com.abc12366.uc.service.einvoice.IEinvoiceService;

/**
 * 电子发票接口
 * @author zhushuai 2017-8-8
 *
 */
@Controller
@RequestMapping(path = "/einvocie")
public class EinvoiceController {
    
	@Autowired
	IEinvoiceService iEinvoiceService;
	
	/**
	 * 电子发票开票/退票
	 * @param dzfpGetReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/get")
	public ResponseEntity getEinvoice(@Valid @RequestBody DzfpGetReq dzfpGetReq){
		Einvocie einvoice=iEinvoiceService.getEinvoice(dzfpGetReq);
		if(einvoice==null){
			return ResponseEntity.ok(Utils.bodyStatus(9999, "电子发票开票/退票异常"));
		}else{
			if("0000".equals(einvoice.getReturnCode())){
				return ResponseEntity.ok(Utils.kv("data", einvoice));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, einvoice.getReturnMessage()));
			}
		}
		
	}
	
	/**
	 * 电子发票查询
	 * @param dzfpGetReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/query")
	public ResponseEntity queryEinvoice(@Valid @RequestBody DzfqQueryReq dzfpGetReq){
		Einvocie einvoice=iEinvoiceService.queryEinvoice(dzfpGetReq);
		if(einvoice==null){
			return ResponseEntity.ok(Utils.bodyStatus(9999, "电子发票查询异常"));
		}else{
			if("0000".equals(einvoice.getReturnCode())){
				return ResponseEntity.ok(Utils.kv("data", einvoice));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, einvoice.getReturnMessage()));
			}
		}
		
	}
	
	/**
	 * 电子发票下载地址查询
	 * @param dzfpGetReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/queryDown")
	public ResponseEntity queryDownEinvoice(@Valid @RequestBody DzfpDownloadReq downloadReq){
		EinvocieDown einvoice=iEinvoiceService.queryEinvoice(downloadReq);
		if(einvoice==null){
			return ResponseEntity.ok(Utils.bodyStatus(9999, "电子发票下载地址查询异常"));
		}else{
			if("0000".equals(einvoice.getReturnCode())){
				return ResponseEntity.ok(Utils.kv("data", einvoice));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, einvoice.getReturnMessage()));
			}
		}
		
	}
	
}
