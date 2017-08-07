package com.abc12366.uc.web.pay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc12366.uc.util.AliPayConfig;
import com.alibaba.fastjson.JSON;

/**
 * 支付接口毁掉地址
 * @author zhushuai 2017-8-4
 *
 */
@Controller()
@RequestMapping(path = "/payreturn")
public class PayReturnController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PayReturnController.class);
     
	@PostMapping("/alipay.do")
	public @ResponseBody String aliPayReturn(HttpServletRequest request){
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			LOGGER.info("支付宝回调信息:",JSON.toJSONString(params));
			boolean signVerified =AliPayConfig.rsaCheckV1(params);  //调用SDK验证签名
			if(signVerified) {//验证成功
				//商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				}else if (trade_status.equals("TRADE_SUCCESS")){
					
				}				
				return "success";				
			}else {//验证失败
				return("fail");
			}
			
		} catch (Exception e) {
			LOGGER.error("支付宝回调处理异常异常,原因:",e);
			return("fail");
		} 
	}
}
