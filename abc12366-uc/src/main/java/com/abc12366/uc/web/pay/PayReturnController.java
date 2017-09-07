package com.abc12366.uc.web.pay;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db2.OrderExchangeRoMapper;
import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.OrderService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.TradeLogService;
import com.abc12366.uc.service.VipLogService;
import com.abc12366.uc.util.AliPayConfig;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 支付接口回调地址
 *
 * @author zhushuai 2017-8-4
 */
@Controller
@RequestMapping(path = "/payreturn")
public class PayReturnController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PayReturnController.class);

    @Autowired
    private TradeLogService tradeLogService;
    @Autowired
    private OrderService orderService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/validate")
    public ResponseEntity validate(@RequestBody Map<String, String> params) {
        LOGGER.info("验证回调信息签名:", JSON.toJSONString(params));
        try {
            return ResponseEntity.ok(Utils.kv("data", AliPayConfig.rsaCheckV1(params)));
        } catch (AlipayApiException e) {
            LOGGER.error("验证回调信息签名异常,原因:", e);
            return ResponseEntity.ok(Utils.bodyStatus(9999, "验证回调信息签名异常:" + e.getMessage()));
        }
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/alipay")
    public ResponseEntity aliPayReturn(HttpServletRequest request) {
        LOGGER.info("{}", request);
        try {
//			Map<String, String> params = new HashMap<String, String>();
//			Map<String, String[]> requestParams = request.getParameterMap();
//			for (Iterator<String> iter = requestParams.keySet().iterator(); iter
//					.hasNext();) {
//				String name = (String) iter.next();
//				String[] values = (String[]) requestParams.get(name);
//				String valueStr = "";
//				for (int i = 0; i < values.length; i++) {
//					valueStr = (i == values.length - 1) ? valueStr + values[i]
//							: valueStr + values[i] + ",";
//				}
//				// 乱码解决，这段代码在出现乱码时使用
//				valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
//				params.put(name, valueStr);
//			}
//
//			LOGGER.info("支付宝回调信息:", JSON.toJSONString(params));
//			boolean signVerified = AliPayConfig.rsaCheckV1(params); // 调用SDK验证签名
//			if (signVerified) {// 验证成功
            LOGGER.info("支付宝回调信息:验证签名成功");
            // 商户订单号
            String out_trade_no = new String(request.getParameter(
                    "out_trade_no").getBytes("utf-8"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no")
                    .getBytes("utf-8"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter(
                    "trade_status").getBytes("utf-8"), "UTF-8");

            String total_amount = new String(request.getParameter(
                    "total_amount").getBytes("utf-8"), "UTF-8");

            String date = new String(request.getParameter("gmt_payment")
                    .getBytes("utf-8"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {// 交易结束

            } else if (trade_status.equals("TRADE_SUCCESS")) {// 交易成功,插入交易记录
                LOGGER.info("支付宝回调信息:交易成功,插入支付流水记录");

                TradeBillBO tradeBillBO = new TradeBillBO();
                tradeBillBO.setTradeNo(trade_no);
                TradeLog tradeLogUpdate = tradeLogService.selectOne(tradeBillBO);
                if (tradeLogUpdate != null) {
                    tradeLogUpdate.setOrderNo(out_trade_no);
                    tradeLogUpdate.setTradeStatus("1");
                    tradeLogUpdate.setTradeType("1");
                    tradeLogUpdate.setAmount(Double.parseDouble(total_amount));
                    tradeLogUpdate.setTradeTime(new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").parse(date));
                    Timestamp now = new Timestamp(new Date().getTime());
                    tradeLogUpdate.setLastUpdate(now);
                    tradeLogUpdate.setPayMethod("ALIPAY");
                    tradeLogService.update(tradeLogUpdate);
                } else {
                    TradeLog tradeLog = new TradeLog();
                    tradeLog.setId(Utils.uuid());
                    tradeLog.setAliTrandeNo(trade_no);
                    tradeLog.setOrderNo(out_trade_no);
                    tradeLog.setTradeStatus("1");
                    tradeLog.setTradeType("1");
                    tradeLog.setAmount(Double.parseDouble(total_amount));
                    tradeLog.setTradeTime(new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").parse(date));
                    Timestamp now = new Timestamp(new Date().getTime());
                    tradeLog.setCreateTime(now);
                    tradeLog.setLastUpdate(now);
                    tradeLog.setPayMethod("ALIPAY");
                    tradeLogService.insertTradeLog(tradeLog);
                    LOGGER.info("支付宝回调信息:插入支付流水记录成功，开始更新订单状态");

                    OrderPayBO orderPayBO = new OrderPayBO();
                    orderPayBO.setOrderNo(out_trade_no);
                    orderPayBO.setIsPay(2);
                    orderPayBO.setPayMethod("ALIPAY");
                    orderService.paymentOrder(orderPayBO);
                    LOGGER.info("更新订单状态:{}", out_trade_no);
                }
            }
            return ResponseEntity.ok(Utils.kv("data", "OK"));
//			} else {// 验证失败
//				LOGGER.info("支付宝回调信息:验证签名失败");
//				return ("fail");
//			}

        } catch (Exception e) {
            LOGGER.error("支付宝回调处理异常,原因:", e);
            return ResponseEntity.ok(Utils.bodyStatus(9999, "支付宝回调处理异常,原因:" + e.getMessage()));
        }
    }
}
