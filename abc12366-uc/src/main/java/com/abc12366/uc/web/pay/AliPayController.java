package com.abc12366.uc.web.pay;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.pay.AliPayReq;
import com.abc12366.uc.model.pay.PayCodeRsp;
import com.abc12366.uc.model.pay.PayqueryReq;
import com.abc12366.uc.model.pay.bo.AliCodePay;
import com.abc12366.uc.util.AliPayConfig;
import com.abc12366.uc.util.QRCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * 支付宝接口
 * @author zhushuai 2017-8-4
 *
 */
@Controller()
@RequestMapping(path = "/alipay")
public class AliPayController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);
    
	/**
	 * 支付宝支付接口,返回支付页面
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/payForm")
	public ResponseEntity aliPayForm(@RequestBody AliPayReq payReq){
		String result = null;
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(payReq.getReturn_url());
			alipayRequest.setNotifyUrl(payReq.getNotify_url());
			alipayRequest.setBizContent(AliPayConfig.toCharsetJsonStr(payReq.getPayContent()));
            result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (Exception e) {
			LOGGER.error("发起支付宝支付异常,原因:",e);
			throw new ServiceException(4103);
		}
		return ResponseEntity.ok(Utils.kv("data", result));
	}
	
	
	/**
	 * 支付宝支付接口,返回二维码
	 * @param payqueryReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/payCode")
	public ResponseEntity aliPayCode(@RequestBody AliCodePay payReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
			request.setBizContent(AliPayConfig.toCharsetJsonStr(payReq));
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
			String result="";
			if(response.isSuccess()){
				result=response.getQrCode();
			}else{
				result="支付宝生成订单失败";
			}
			PayCodeRsp payCodeRsp=new PayCodeRsp(response.getOutTradeNo(),QRCodeUtil.getCreatQRcodeString(result, 400, "JPG"));
			return ResponseEntity.ok(Utils.kv("data", payCodeRsp));
		} catch (Exception e) {
			LOGGER.error("发起支付宝支付异常,原因:",e);
			throw new ServiceException(4103);
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/aliPayQuery")
	public ResponseEntity aliPayQuery(PayqueryReq payqueryReq){
		AlipayClient alipayClient = AliPayConfig.getInstance();
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		alipayRequest.setBizContent(JSON.toJSONString(payqueryReq));
		AlipayTradeQueryResponse response = null;
		try {
			response = alipayClient.execute(alipayRequest);
		} catch (AlipayApiException e) {
			LOGGER.error("支付宝支付J结果查询异常,原因:",e);
			throw new ServiceException(4103);
		}
		return ResponseEntity.ok(Utils.kv("data", null));
	}
	
	public static void main(String[] args) throws Exception {
		
		  
	}
	
}
