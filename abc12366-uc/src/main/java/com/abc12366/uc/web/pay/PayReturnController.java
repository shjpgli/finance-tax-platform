package com.abc12366.uc.web.pay;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.model.bo.OrderPayBO;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.service.TradeLogService;
import com.abc12366.uc.util.AliPayConfig;
import com.alibaba.fastjson.JSON;

/**
 * 支付接口毁掉地址
 * 
 * @author zhushuai 2017-8-4
 * 
 */
@Controller()
@RequestMapping(path = "/payreturn")
public class PayReturnController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PayReturnController.class);

	@Autowired
	private TradeLogService tradeLogService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/alipay.do")
	public @ResponseBody
	String aliPayReturn(HttpServletRequest request) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			LOGGER.info("支付宝回调信息:", JSON.toJSONString(params));
			boolean signVerified = AliPayConfig.rsaCheckV1(params); // 调用SDK验证签名
			if (signVerified) {// 验证成功
				// 商户订单号
				String out_trade_no = new String(request.getParameter(
						"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no")
						.getBytes("ISO-8859-1"), "UTF-8");

				// 交易状态
				String trade_status = new String(request.getParameter(
						"trade_status").getBytes("ISO-8859-1"), "UTF-8");

				String total_amount = new String(request.getParameter(
						"total_amount").getBytes("ISO-8859-1"), "UTF-8");

				String date = new String(request.getParameter("gmt_payment")
						.getBytes("ISO-8859-1"), "UTF-8");

				if (trade_status.equals("TRADE_FINISHED")) {// 交易结束

				} else if (trade_status.equals("TRADE_SUCCESS")) {// 交易成功,插入交易记录
					LOGGER.info("支付宝回调信息:交易成功,插入支付流水记录");

					TradeLog tradeLog = new TradeLog();
					tradeLog.setId(Utils.uuid());
					tradeLog.setOrderNo(out_trade_no);
					tradeLog.setAliTrandeNo(trade_no);
					tradeLog.setTradeStatus("1");
					tradeLog.setTradeType("1");
					tradeLog.setAmount(Double.parseDouble(total_amount));
					tradeLog.setTradeTime(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(date));
					Timestamp now = new Timestamp(new Date().getTime());
					tradeLog.setCreateTime(now);
					tradeLog.setLastUpdate(now);
					tradeLog.setPayMethod("ALIPAY");
					if (tradeLogService.insertTradeLog(tradeLog) == 1) {
						OrderPayBO orderPayBO = new OrderPayBO();
						orderPayBO.setOrderNo(out_trade_no);
						orderPayBO.setIsPay(2);
						orderService.paymentOrder(orderPayBO);
					}
				}
				return "success";
			} else {// 验证失败
				return ("fail");
			}

		} catch (Exception e) {
			LOGGER.error("支付宝回调处理异常异常,原因:", e);
			return ("fail");
		}
	}
}
