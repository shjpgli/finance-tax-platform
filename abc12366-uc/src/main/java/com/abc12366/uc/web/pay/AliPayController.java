package com.abc12366.uc.web.pay;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.pay.AliPayReq;
import com.abc12366.uc.model.pay.BillListReq;
import com.abc12366.uc.model.pay.PayCodeRsp;
import com.abc12366.uc.model.pay.PayqueryReq;
import com.abc12366.uc.model.pay.RefundQueryReq;
import com.abc12366.uc.model.pay.bo.AliCodePay;
import com.abc12366.uc.model.pay.bo.AliRefund;
import com.abc12366.uc.util.AliPayConfig;
import com.abc12366.uc.util.QRCodeUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 支付宝支付接口
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
	@PostMapping("/payform")
	public ResponseEntity aliPayForm(@RequestBody AliPayReq payReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(payReq.getReturn_url());
			alipayRequest.setNotifyUrl(payReq.getNotify_url());
			alipayRequest.setBizContent(AliPayConfig.toCharsetJsonStr(payReq.getPayContent()));
			String result = alipayClient.pageExecute(alipayRequest).getBody();
            return ResponseEntity.ok(Utils.kv("data", result));
		} catch (Exception e) {
			LOGGER.error("发起支付宝支付异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "发起支付宝支付异常"));
		}
		
	}
	
	
	/**
	 * 支付宝支付接口,返回二维码
	 * @param payReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/paycode")
	public ResponseEntity aliPayCode(@RequestBody AliCodePay payReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
			request.setBizContent(AliPayConfig.toCharsetJsonStr(payReq));
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				PayCodeRsp payCodeRsp=new PayCodeRsp(response.getOutTradeNo(),QRCodeUtil.getCreatQRcodeString(response.getQrCode(), payReq.getQrCodeSize(), "JPG"));
				return ResponseEntity.ok(Utils.kv("data", payCodeRsp));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("发起支付宝支付异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "发起支付宝支付异常"));
		}
		
	}
	
	/**
	 * 交易结果查询
	 * @param payqueryReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/alipayquery")
	public ResponseEntity aliPayQuery(PayqueryReq payqueryReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
			alipayRequest.setBizContent(AliPayConfig.toCharsetJsonStr(payqueryReq));
			AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝支付结果查询异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝支付结果查询"));
		}
	}
	
	/**
	 * 支付宝退款
	 * @param aliRefund
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/refund")
	public ResponseEntity aliPayRefund(@RequestBody AliRefund aliRefund){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent(AliPayConfig.toCharsetJsonStr(aliRefund));
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝退款异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝退款异常"));
		}
	}
	
	/**
	 * 退款结果查询
	 * @param out_trade_no
	 * @param out_request_no
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/refund/{out_trade_no}/{out_request_no}")
	public ResponseEntity aliPayRefundQuery(@PathVariable("out_trade_no") String out_trade_no,
			@PathVariable("out_request_no") String out_request_no){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradeFastpayRefundQueryRequest  request = new AlipayTradeFastpayRefundQueryRequest ();
			request.setBizContent(AliPayConfig.toCharsetJsonStr(new RefundQueryReq(out_trade_no,out_request_no)));
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝退款异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝退款异常"));
		}
	}
	/**
	 * 交易关闭
	 * @param payqueryReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/alipayclose")
	public ResponseEntity aliPayClose(PayqueryReq payqueryReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
			alipayRequest.setBizContent(AliPayConfig.toCharsetJsonStr(payqueryReq));
			AlipayTradeCloseResponse response = alipayClient.execute(alipayRequest);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝支付交易关闭异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝支付交易关闭异常"));
		}
	}
	
	/**
	 * 交易取消
	 * @param payqueryReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/alipaycancel")
	public ResponseEntity aliPayCancel(PayqueryReq payqueryReq){
		try {
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayTradeCancelRequest alipayRequest = new AlipayTradeCancelRequest();
			alipayRequest.setBizContent(AliPayConfig.toCharsetJsonStr(payqueryReq));
			AlipayTradeCancelResponse response = alipayClient.execute(alipayRequest);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝支付交易取消异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝支付交易取消异常"));
		}
	}
	
	/**
	 * 交易对账单
	 * @param payqueryReq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/alipaylis")
	public ResponseEntity aliPaylist(@RequestParam(value="bill_date",required=true) String bill_date){
		try {
			BillListReq billListReq=new BillListReq();
			billListReq.setBill_date(bill_date);
			AlipayClient alipayClient = AliPayConfig.getInstance();
			AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent(AliPayConfig.toCharsetJsonStr(billListReq));
			AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				return ResponseEntity.ok(Utils.kv("data", response));
			}else{
				return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
			}
		} catch (Exception e) {
			LOGGER.error("支付宝支付交易取消异常,原因:",e);
			return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝支付交易取消异常"));
		}
	}
}
