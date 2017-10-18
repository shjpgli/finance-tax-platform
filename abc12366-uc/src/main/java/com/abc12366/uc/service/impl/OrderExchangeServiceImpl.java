package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceDetailMapper;
import com.abc12366.uc.mapper.db1.OrderExchangeMapper;
import com.abc12366.uc.mapper.db1.OrderLogMapper;
import com.abc12366.uc.mapper.db1.OrderMapper;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.model.dzfp.InvoiceXm;
import com.abc12366.uc.model.invoice.InvoiceDetail;
import com.abc12366.uc.model.pay.RefundRes;
import com.abc12366.uc.model.pay.bo.AliRefund;
import com.abc12366.uc.service.OrderExchangeService;
import com.abc12366.uc.service.PointsLogService;
import com.abc12366.uc.service.TradeLogService;
import com.abc12366.uc.util.*;
import com.abc12366.uc.webservice.DzfpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
@Service("orderExchangeService")
public class OrderExchangeServiceImpl implements OrderExchangeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderExchangeServiceImpl.class);
    @Autowired
    private OrderExchangeMapper orderExchangeMapper;

    @Autowired
    private OrderExchangeRoMapper orderExchangeRoMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private DictRoMapper dictRoMapper;

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderRoMapper orderRoMapper;

    @Autowired
    private TradeLogRoMapper tradeLogRoMapper;

    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    @Autowired
    private InvoiceDetailRoMapper invoiceDetailRoMapper;

    @Autowired
    private PointsLogService pointsLogService;
    @Autowired
	private TradeLogService tradeLogService;

    @Autowired
    private ExpressCompRoMapper expressCompRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private UserAddressRoMapper userAddressRoMapper;

    @Transactional("db1TxManager")
    @Override
    public OrderExchange insert(ExchangeApplicationBO ra) {
        exchangeCheck(ra);
        OrderExchange data = new OrderExchange();
        BeanUtils.copyProperties(ra, data);
        //查询地址信息
        if(ra != null && ra.getAddressId() != null && !"".equals(ra.getAddressId())){
            UserAddressBO userAddress = userAddressRoMapper.selectById(ra.getAddressId());
            if(userAddress != null){
                StringBuffer address = new StringBuffer();
                address.append(userAddress.getProvinceName() + "-");
                address.append(userAddress.getCityName() + "-");
                address.append(userAddress.getAreaName() + "-");
                address.append(userAddress.getDetail());
                data.setConsignee(userAddress.getName());
                data.setContactNumber(userAddress.getPhone());
                data.setShippingAddress(address.toString());
            }
        }

        List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
        //OrderExchange orderExchange = orderExchangeRoMapper.selectByOrderNo(data.getOrderNo());
        if (dataList != null && dataList.size() > 0) {
            throw new ServiceException(4950);
        } else {
            String userId = Utils.getUserId();
            data.setId(Utils.uuid());
            data.setUserId(userId);
            Timestamp now = new Timestamp(new Date().getTime());
            data.setCreateTime(now);
            data.setLastUpdate(now);
            data.setStatus("1");
            orderExchangeMapper.insert(data);

            // 插入订单日志
            insertLog(data.getOrderNo(), "1", userId, data.getUserRemark(),"1");
            // 更新订单状态
            //changeOrderStatus(data.getOrderNo());
        }
        return data;
    }

    private void changeOrderStatus(String orderNo) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus("6");
        order = orderRoMapper.selectOne(order);
        if (order != null) {
            order.setOrderStatus("10");
            order.setLastUpdate(new Date());
            orderMapper.update(order);

            //insertLog(order.getOrderNo(), "orderStatus", "10", Utils.getUserId(), "用户提交退换货申请","1");
        }
    }

    private void exchangeCheck(ExchangeApplicationBO ra) {
        ExchangeCompletedOrderBO bo = orderExchangeRoMapper.selectCompletedOrder(ra.getOrderNo());
        if (bo == null) { // 是否为已完成订单
            throw new ServiceException(4951);
        }
        if (!Utils.getUserId().equals(bo.getUserId())) {
            throw new ServiceException(4961);
        }
        if ("1".equals(ra.getType())) { // 换货
            if ("1".equals(bo.getIsExchange())) { // 是否为实物商品换货
                throw new ServiceException(4952,"订单中<"+bo.getName()+">商品，不能换货");
            }
            // 是否在换货日期之内
            if (new Date().after(DateUtils.addDays(new Date(bo.getLastUpdate().getTime()), Constant
                    .ORDER_EXCHANGE_DAYS))) {
                throw new ServiceException(4953,"您的订单已超过"+Constant.ORDER_EXCHANGE_DAYS+"天的换货日，不支持换货");
            }
        } else { // 退货
            if ("1".equals(bo.getIsReturn())) { // 虚拟商品暂时不支持退货
                throw new ServiceException(4953,"订单中<"+bo.getName()+">商品，不能退货");
            }
            // 是否在退货日期之内
            if (new Date().after(DateUtils.addDays(new Date(bo.getLastUpdate().getTime()), Constant.ORDER_BACK_DAYS))) {
                throw new ServiceException(4953,"您的订单已超过"+Constant.ORDER_BACK_DAYS+"天的退货日，不支持退货");
            }
            // 用户现有积分是否达到购买是赠送的积分
            if (bo.getPoints() < bo.getGiftPoints()) {
                throw new ServiceException(4960);
            }
            // 查询发票申请是否结束状态
            List<ExchangeOrderInvoiceBO> dataList = orderExchangeRoMapper.selectOrderInvoice(ra.getOrderNo());
            if (dataList.size() > 0) {
                throw new ServiceException(4955);
            }
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange update(ExchangeApplicationBO data) {
        exchangeCheck(data);
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null && "5".equals(oe.getStatus())) {
            //查询地址信息
            if(data != null && data.getAddressId() != null && !"".equals(data.getAddressId())){
                UserAddressBO userAddress = userAddressRoMapper.selectById(data.getAddressId());
                if(userAddress != null){
                    StringBuffer address = new StringBuffer();
                    address.append(userAddress.getProvinceName() + "-");
                    address.append(userAddress.getCityName() + "-");
                    address.append(userAddress.getAreaName() + "-");
                    address.append(userAddress.getDetail());
                    oe.setConsignee(userAddress.getName());
                    oe.setContactNumber(userAddress.getPhone());
                    oe.setShippingAddress(address.toString());
                }
            }
            //List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
            oe.setReason(data.getReason());
            oe.setUserRemark(data.getUserRemark());
            oe.setType(data.getType());
            oe.setLastUpdate(new Timestamp(new Date().getTime()));
            oe.setStatus("1");
            oe.setAdminRemark("");
            oe.setExpressNo("");
            oe.setExpressComp("");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(data.getOrderNo(), "1", Utils.getUserId(), data.getUserRemark(),"1");
        } else {
            throw new ServiceException(4962);
        }
        return oe;
    }

    @Override
    public List<SfExportBO> export(OrderExchangeExportBO data) {
        return orderExchangeRoMapper.export(data);
    }

    @Transactional("db1TxManager")
    @Override
    public void importJson(List<SfImportBO> dataList, HttpServletRequest request) {
        if (dataList.size() > 0) {
            for (SfImportBO data : dataList) {
                OrderExchange oe = new OrderExchange.Builder()
                        .orderNo(data.getOrderNo())
                        .toExpressNo(data.getExpressNo())
                        .expressComp(data.getExpressComp())
                        .status("3")
                        .build();
                orderExchangeMapper.update(oe);
                // 插入订单日志
                insertLog(oe.getOrderNo(), "3", Utils.getAdminId(), oe.getAdminRemark(),"1");


                //发送消息
                Order order = orderRoMapper.selectByPrimaryKey(oe.getOrderNo());
                if(order == null){
                    LOGGER.warn("订单信息查询失败：{}", oe.getOrderNo());
                    throw new ServiceException(4102,"订单信息查询失败");
                }
                ExpressComp expressComp = expressCompRoMapper.selectByPrimaryKey(order.getExpressCompId());
                if(expressComp == null){
                    LOGGER.warn("物流公司查询失败：{}", order.getExpressCompId());
                    throw new ServiceException(4102,"物流公司查询失败");
                }
                Message message = new Message();
                message.setBusinessId(order.getOrderNo());
                message.setType("SPDD");
                message.setContent(MessageConstant.EXCHANGE_DELIVER_GOODS_PREFIX+expressComp.getCompName()+"+"+order.getExpressNo()+MessageConstant.SUFFIX);
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange back(ExchangeBackBO data, HttpServletRequest request) throws Exception {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            // 更新退货状态：确认退货
            oe.setExpressNo(data.getExpressNo());
            oe.setExpressComp(data.getExpressComp());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(new Timestamp(new Date().getTime()));
            oe.setStatus("7");
            oe.setAdminConfirmRemark(data.getAdminConfirmRemark());
            orderExchangeMapper.update(oe);

            // 更新订单状态：已退单
            /*Order order = new Order();
            order.setOrderNo(oe.getOrderNo());
            order.setOrderStatus("9");
            order.setLastUpdate(new Timestamp(new Date().getTime()));
            orderMapper.update(order);*/

            // 发票作废
            List<ExchangeOrderInvoiceBO> orderInvoiceBOList = orderExchangeRoMapper.selectInvoice(oe.getOrderNo());

            if (orderInvoiceBOList.size() > 0) {
                DzfpGetReq req = new DzfpGetReq();
                List<InvoiceXm> dataList = new ArrayList<>();
                for (ExchangeOrderInvoiceBO eoi : orderInvoiceBOList) {
                    if ("1".equals(eoi.getProperty())) { // 纸质发票
                        InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectByInvoiceNo(eoi.getInvoiceNo());
                        if (invoiceDetail != null) {
                            // 更新发票状态：3-已作废
                            invoiceDetail.setStatus("3");
                            invoiceDetail.setLastUpdate(new Date());
                            invoiceDetailMapper.update(invoiceDetail);
                        }
                    } else if ("2".equals(eoi.getProperty())) { // 电子发票
                        if ("1".equals(eoi.getName())) { // 个人发票
                            req.setGmf_mc(eoi.getNsrmc());
                        } else {
                            req.setGmf_mc(eoi.getNsrmc());
                            req.setGmf_nsrsbh(eoi.getNsrsbh());
                            req.setGmf_dzdh(eoi.getAddress());
                            req.setGmf_yhzh(eoi.getBank());
                            req.setGmf_sjh(eoi.getPhone());
                        }
                        req.setKplx("1");
                        req.setZsfs("0");
                        req.setKpr(UserUtil.getAdminInfo().getNickname());
                        req.setHylx("0");

                        InvoiceXm xm = new InvoiceXm();
                        xm.setFphxz("0");
                        xm.setXmmc(selectFieldValue("invoicecontent", eoi.getContent()));
                        xm.setXmsl(-1.00);
                        xm.setTotalAmt(-eoi.getAmount());
                        dataList.add(xm);

                        req.setInvoiceXms(dataList);
                    }
                }
                Einvocie einvocie = (Einvocie) DzfpClient.doSender("DFXJ1001", req.tosendXml(), Einvocie.class);

                if ("0000".equals(einvocie.getReturnCode())) { // 更新作废状态
                    InvoiceDetail id = invoiceDetailRoMapper.selectByInvoiceNo(einvocie.getFP_HM());
                    id.setStatus("3");
                    id.setLastUpdate(new Date());
                    id.setSpUrl(einvocie.getSP_URL());
                    id.setPdfUrl(einvocie.getPDF_URL());
                    invoiceDetailMapper.update(id);
                } else {
                    throw new ServiceException(einvocie.getReturnCode(), einvocie.getReturnMessage());
                }
            }
            // 插入订单日志
            insertLog(oe.getOrderNo(), "7", Utils.getAdminId(), oe.getAdminConfirmRemark(),"1");

            //发送消息
//            Order order = orderRoMapper.selectByPrimaryKey(oe.getOrderNo());
//            if(order == null){
//                LOGGER.warn("订单信息查询失败：{}", oe.getOrderNo());
//                throw new ServiceException(4102,"订单信息查询失败");
//            }
//            ExpressComp expressComp = expressCompRoMapper.selectByPrimaryKey(order.getExpressCompId());
//            if(expressComp == null){
//                LOGGER.warn("物流公司查询失败：{}", order.getExpressCompId());
//                throw new ServiceException(4102,"物流公司查询失败");
//            }
//            Message message = new Message();
//            message.setBusinessId(oe.getOrderNo());
//            message.setType("SPDD");
//            message.setContent("您的宝贝已发出，运单号：（"+data.getExpressComp()+"+"+order.getExpressNo()+"），如有疑问请联系客服咨询4008873133");
//            message.setUserId(order.getUserId());
//            messageSendUtil.sendMessage(message, request);
        }
        return oe;
    }

    @Transactional("db1TxManager")
    @Override
    public ResponseEntity refund(ExchangeRefundBO data, HttpServletRequest httpServletRequest) {
    	OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());
    	// 退积分
        ExchangeCompletedOrderBO eco = orderExchangeRoMapper.selectCompletedOrder(oe.getOrderNo());
        if (eco != null && eco.getGiftPoints() < eco.getPoints()) {
            PointsLogBO pointsLog = new PointsLogBO();
            pointsLog.setRuleId(oe.getOrderNo());
            pointsLog.setOutgo(eco.getGiftPoints());
            pointsLog.setUserId(eco.getUserId());
            pointsLog.setRemark("用户退单");
            pointsLog.setLogType("ORDER_BACK");
            pointsLogService.insert(pointsLog);
        } else {
            throw new ServiceException(4960);
        }

        //查询订单信息
        Order order = orderRoMapper.selectByPrimaryKey(oe.getOrderNo());
        //判断是RMB、积分
        if(order != null && "RMB".equals(order.getTradeMethod())){
            if ("ALIPAY".equals(order.getPayMethod())) {
                // 查询交易日志中支付成功的订单
                TradeLog log = new TradeLog();
                log.setOrderNo(oe.getOrderNo());
                log.setTradeStatus("1");
                log.setPayMethod("ALIPAY");
                List<TradeLog> logList = tradeLogRoMapper.selectList(log);

                if(data.getAmount() >= order.getTotalPrice()){
                    LOGGER.info("退款金额不能大于订单成交总金额，{}",data.getAmount());
                    throw new ServiceException(4919);
                }
                if (logList.size() > 0) {
                    for (int i = 0; i < logList.size(); i++) {
                        if (logList.get(i).getAmount() >= data.getAmount()) {
                            AliRefund refund = new AliRefund();
                            refund.setOut_trade_no(logList.get(i).getOrderNo());
                            refund.setTrade_no(logList.get(i).getAliTrandeNo());
                            refund.setRefund_amount(String.valueOf(data.getAmount()));
                            refund.setRefund_reason(data.getAdminRemark());
                            String out_request_no=log.getOrderNo()+"_"+logList.size();
                            refund.setOut_request_no(out_request_no);

                            try {
                                AlipayClient alipayClient = AliPayConfig.getInstance();
                                AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
                                request.setBizContent(AliPayConfig.toCharsetJsonStr(refund));
                                AlipayTradeRefundResponse response = alipayClient.execute(request);
                                LOGGER.info("支付宝退款支付宝返回信息{}",JSON.toJSONString(response));
                                if(response.isSuccess()){

                                    JSONObject object = JSON.parseObject(response.getBody());
                                    RefundRes refundRes=JSON.parseObject(object.getString("alipay_trade_refund_response"), RefundRes.class);

                                    LOGGER.info("支付宝退款成功,插入退款流水记录");
                                    TradeLog tradeLog=new TradeLog();
                                    tradeLog.setId(Utils.uuid());
                                    tradeLog.setOrderNo(out_request_no);
                                    tradeLog.setAliTrandeNo(refundRes.getTrade_no());
                                    tradeLog.setTradeStatus("1");
                                    tradeLog.setTradeType("2");
                                    tradeLog.setAmount(Double.parseDouble("-"+refundRes.getRefund_fee()));
                                    tradeLog.setTradeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(refundRes.getGmt_refund_pay()));
                                    Timestamp now = new Timestamp(new Date().getTime());
                                    tradeLog.setCreateTime(now);
                                    tradeLog.setLastUpdate(now);
                                    tradeLog.setPayMethod("ALIPAY");
                                    tradeLogService.insertTradeLog(tradeLog);

                                    oe.setStatus("8");
                                    oe.setAdminRemark(data.getAdminRemark());
                                    oe.setLastUpdate(new Timestamp(new Date().getTime()));
                                    orderExchangeMapper.update(oe);
                                    // 插入订单日志-已退款
                                    // insertLog(oe.getOrderNo(), "8", Utils.getAdminId(), oe.getAdminRemark(),"1");


                                    // 插入订单日志-已完成
                                    insertLog(oe.getOrderNo(), "4", Utils.getAdminId(), "已完成退款","1");

                                    //发送消息
                                    if(order == null){
                                        LOGGER.warn("订单信息查询失败：{}", oe.getOrderNo());
                                        throw new ServiceException(4102,"订单信息查询失败");
                                    }
                                    //服务类型：1-换货 2-退货
                                    Message message = new Message();
                                    message.setBusinessId(oe.getOrderNo());
                                    message.setType(MessageConstant.SPDD);
                                    message.setContent(MessageConstant.REFUND_PREFIX+refundRes.getRefund_fee()+MessageConstant.REFUND_SUFFIX+order.getOrderNo());
                                    message.setUrl("<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.VIEW_DETAILS+"</a>");
                                    message.setUserId(order.getUserId());
                                    messageSendUtil.sendMessage(message, httpServletRequest);

                                    return ResponseEntity.ok(Utils.kv("data", refundRes));
                                }else{
                                    return ResponseEntity.ok(Utils.bodyStatus(9999, response.getSubMsg()));
                                }
                            }  catch (Exception e) {
                                LOGGER.error("支付宝退款失败：",e);
                                return ResponseEntity.ok(Utils.bodyStatus(9999, e.getMessage()));
                            }
                        }
                    }
                }

            } else {
                throw new ServiceException(4956);
            }
        }else if(order != null && "POINTS".equals(order.getTradeMethod())){
            insertPoints(order);
        }else{
            throw new ServiceException(4957);
        }
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 插入可获得的积分
     * @param orderBO
     */
    private void insertPoints(Order orderBO) {
        PointsLogBO pointsLog = new PointsLogBO();
        pointsLog.setUserId(orderBO.getUserId());
        pointsLog.setRuleId(UCConstant.POINT_RULE_ORDER_RETURN_ID);
        pointsLog.setId(Utils.uuid());
        //成交总积分 - 赠送积分
        pointsLog.setIncome((int) (orderBO.getTotalPrice() - orderBO.getGiftPoints()));
        pointsLog.setRemark("用户退单- 订单号："+orderBO.getOrderNo());
        pointsLog.setLogType("ORDER_EXCHANGE");
        pointsLogService.insert(pointsLog);
    }

    @Transactional("db1TxManager")
    @Override
    public void automaticReceipt() throws Exception {
        Timestamp now = new Timestamp(new Date().getTime());
        OrderExchange oe = new OrderExchange.Builder()
                .status("3")
                .lastUpdate(now)
                .build();
        List<OrderExchange> dataList = orderExchangeRoMapper.selectList(oe);
        // 更新退换货状态
        dataList.stream().filter(data -> data.getLastUpdate().after(DateUtils.addDays(new Date(), -Constant
                .ORDER_EXCHANGE_RECEIPT_DAYS))).forEach(data -> {
            data.setStatus("4");
            data.setLastUpdate(now);
            orderExchangeMapper.update(data);

            // 更新订单状态
            /*Order order = new Order();
            order.setOrderNo(data.getOrderNo());
            order.setOrderStatus("10");
            order = orderRoMapper.selectOne(order);
            if (order != null) {
                order.setOrderStatus("6");
                order.setLastUpdate(new Date());
                orderMapper.update(order);
            }*/
            // 插入订单日志
            insertLog(data.getOrderNo(), "4", "", "系统自动完成收货","1");
        });
    }

    @Override
    public List<ExchangeOrderInvoiceBO> selectInvoice(String orderNo) {
        return orderExchangeRoMapper.selectInvoice(orderNo);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange receive(String id) {
        OrderExchange oe = orderExchangeRoMapper.selectById(id);
        oe.setStatus("4");
        oe.setLastUpdate(new Timestamp(new Date().getTime()));
        orderExchangeMapper.update(oe);

        // 插入订单日志
        insertLog(oe.getOrderNo(), "4", Utils.getUserId(), "用户确认收货","1");
        return oe;
    }


    @Override
    public List<OrderExchange> selectList(OrderExchange oe, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return orderExchangeRoMapper.selectList(oe);
    }

    @Override
    public List<OrderExchange> selectListForFinance(OrderExchange oe, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return orderExchangeRoMapper.selectListForFinance(oe);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange disagree(ExchangeAdminBO data, HttpServletRequest request) {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("5");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "5", Utils.getAdminId(), oe.getAdminRemark(),"1");

            //发送消息
            Order order = orderRoMapper.selectByPrimaryKey(oe.getOrderNo());
            if(order == null){
                LOGGER.warn("订单信息查询失败：{}", oe.getOrderNo());
                throw new ServiceException(4102,"订单信息查询失败");
            }
            //服务类型：1-换货 2-退货
            if("1".equals(oe.getType())){
                Message message = new Message();
                message.setBusinessId(oe.getOrderNo());
                message.setType(MessageConstant.SPDD);
                message.setContent(MessageConstant.EXCHANGE_CHECK_REFUSE+order.getOrderNo()+MessageConstant.SUFFIX);
                message.setUrl("<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.VIEW_DETAILS+"</a>");
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }else if("2".equals(oe.getType())){
                Message message = new Message();
                message.setBusinessId(oe.getOrderNo());
                message.setType(MessageConstant.SPDD);
                message.setContent(MessageConstant.RETREAT_CHECK_REFUSE+order.getOrderNo()+MessageConstant.SUFFIX);
                message.setUrl("<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.VIEW_DETAILS+"</a>");
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }
        }
        return oe;
    }

    @Override
    public OrderExchange selectOne(String id) {
        return orderExchangeRoMapper.selectOne(id);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange agree(ExchangeAdminBO data, HttpServletRequest request) {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("2");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "2", Utils.getAdminId(), oe.getAdminRemark(),"1");

            //发送消息
            Order order = orderRoMapper.selectByPrimaryKey(oe.getOrderNo());
            if(order == null){
                LOGGER.warn("订单信息查询失败：{}", order.getExpressCompId());
                throw new ServiceException(4102,"订单信息查询失败");
            }
            //服务类型：1-换货 2-退货
            if("1".equals(oe.getType())){
                Message message = new Message();
                message.setBusinessId(oe.getOrderNo());
                message.setType(MessageConstant.SPDD);
                message.setContent(MessageConstant.EXCHANGE_CHECK_ADOPT);
                message.setUrl("<a href=\"" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/orderback/exchange/" + oe.getId() + "/" + order.getOrderNo() + "\">" + MessageConstant.VIEW_DETAILS + "</a>");
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }else if("2".equals(oe.getType())){
                Message message = new Message();
                message.setBusinessId(oe.getOrderNo());
                message.setType(MessageConstant.SPDD);
                message.setContent(MessageConstant.RETREAT_CHECK_ADOPT);
                message.setUrl("<a href=\""+SpringCtxHolder.getProperty("abc12366.api.url.uc")+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.VIEW_DETAILS+"</a>");
                message.setUserId(order.getUserId());
                messageSendUtil.sendMessage(message, request);
            }
        }
        return oe;
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange confirm(ExchangeConfirmBO data) {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setExpressNo(data.getExpressNo());
            oe.setExpressComp(data.getExpressComp());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("6");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "6", Utils.getAdminId(), oe.getAdminRemark(),"1");
        }
        return oe;
    }

    private List<OrderExchange> selectUndoneList(String orderNo) {
        return orderExchangeRoMapper.selectUndoneList(orderNo);
    }

    private String selectFieldValue(String fieldValue, String value) {
        Dict dict = new Dict();
        dict.setDictId(fieldValue);
        dict.setFieldValue(value);
        dict = dictRoMapper.selectOne(dict);
        return dict != null ? dict.getFieldKey() : "";
    }

    private void insertLog(String orderNo, String status, String userId, String remark,String logType) {
        // 插入订单日志
        OrderLog ol = new OrderLog.Builder()
                .id(Utils.uuid())
                .orderNo(orderNo)
                .action(selectFieldValue("exchange_status", status))
                .createTime(new Timestamp(new Date().getTime()))
                .createUser(userId)
                .remark(remark)
                .logType(logType)
                .build();
        orderLogMapper.insert(ol);
    }
    
    /*private void insertLog(String orderNo, String dictId, String status, String userId, String remark,String logType) {
        // 插入订单日志
        OrderLog ol = new OrderLog.Builder()
            .id(Utils.uuid())
            .orderNo(orderNo)
            .action(selectFieldValue(dictId, status))
            .createTime(new Timestamp(new Date().getTime()))
            .createUser(userId)
            .remark(remark)
            .logType(logType)
            .build();
        orderLogMapper.insert(ol);
    }*/
}
