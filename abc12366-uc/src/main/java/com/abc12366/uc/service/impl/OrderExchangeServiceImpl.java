package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.jrxt.model.util.DateUtil;
import com.abc12366.uc.mapper.db1.InvoiceDetailMapper;
import com.abc12366.uc.mapper.db1.OrderExchangeMapper;
import com.abc12366.uc.mapper.db1.OrderLogMapper;
import com.abc12366.uc.mapper.db1.OrderMapper;
import com.abc12366.uc.mapper.db2.DictRoMapper;
import com.abc12366.uc.mapper.db2.InvoiceDetailRoMapper;
import com.abc12366.uc.mapper.db2.OrderExchangeRoMapper;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.abc12366.uc.model.dzfp.InvoiceXm;
import com.abc12366.uc.model.invoice.InvoiceDetail;
import com.abc12366.uc.model.pay.RefundRes;
import com.abc12366.uc.model.pay.bo.AliRefund;
import com.abc12366.uc.service.OrderExchangeService;
import com.abc12366.uc.service.TradeLogService;
import com.abc12366.uc.util.UserUtil;
import com.abc12366.uc.web.pay.AliPayController;
import com.abc12366.uc.webservice.DzfpClient;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
@Service
public class OrderExchangeServiceImpl implements OrderExchangeService {

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
    private TradeLogService tradeLogService;

    @Autowired
    private AliPayController aliPayController;

    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    @Autowired
    private InvoiceDetailRoMapper invoiceDetailRoMapper;

    @Transactional("db1TxManager")
    @Override
    public OrderExchange insert(ExchangeApplicationBO ra) {
        exchangeCheck(ra);

        OrderExchange data = new OrderExchange();
        BeanUtils.copyProperties(ra, data);
        List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
        if (dataList.size() == 0) {
            String userId = Utils.getUserId();
            data.setId(Utils.uuid());
            data.setUserId(userId);
            Timestamp now = new Timestamp(new Date().getTime());
            data.setCreateTime(now);
            data.setLastUpdate(now);
            data.setStatus("1");
            orderExchangeMapper.insert(data);

            // 插入订单日志
            insertLog(data.getOrderNo(), "1", userId, data.getUserRemark());
        } else {
            throw new ServiceException(4950);
        }
        return data;
    }

    private void exchangeCheck(ExchangeApplicationBO ra) {
        ExchangeCompletedOrderBO bo = orderExchangeRoMapper.selectCompletedOrder(ra.getOrderNo());
        if (bo == null) { // 是否为已完成订单
            throw new ServiceException(4951);
        }
        if ("1".equals(ra.getType())) { // 换货
            if (!"2".equals(bo.getGoodsType())) { // 是否为实物商品换货
                throw new ServiceException(4952);
            }
            // 是否在换货日期之内
            if (new Date().after(DateUtils.addDays(new Date(bo.getLastUpdate().getTime()), Constant
                    .ORDER_EXCHANGE_DAYS))) {
                throw new ServiceException(4953);
            }
        } else { // 退货
            if ("1".equals(bo.getGoodsType())) { // 虚拟商品暂时不支持退货
                throw new ServiceException(4954);
            }
            // 是否在换货日期之内
            if (new Date().after(DateUtils.addDays(new Date(bo.getLastUpdate().getTime()), Constant.ORDER_BACK_DAYS))) {
                throw new ServiceException(4953);
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
        if (oe != null) {
            List<OrderExchange> dataList = selectUndoneList(data.getOrderNo());
            if (dataList.size() == 0) {
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
                insertLog(data.getOrderNo(), "1", Utils.getUserId(), data.getUserRemark());
            } else {
                throw new ServiceException(4950);
            }
        }
        return oe;
    }

    @Override
    public List<SfExportBO> export(OrderExchangeExportBO data) {
        return orderExchangeRoMapper.export(data);
    }

    @Transactional("db1TxManager")
    @Override
    public void importJson(List<SfImportBO> dataList) {
        if (dataList.size() > 0) {
            for (SfImportBO data : dataList) {
                OrderExchange oe = new OrderExchange.Builder()
                        .orderNo(data.getOrderNo())
                        .toExpressNo(data.getExpressNo())
                        .status("3")
                        .build();
                orderExchangeMapper.update(oe);
                // 插入订单日志
                insertLog(oe.getOrderNo(), "3", Utils.getAdminId(), oe.getAdminRemark());
            }
        }
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange back(ExchangeBackBO data) throws Exception {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            // 更新退货状态：确认退货
            oe.setExpressNo(data.getExpressNo());
            oe.setExpressComp(data.getExpressComp());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(new Timestamp(new Date().getTime()));
            oe.setStatus("7");
            orderExchangeMapper.update(oe);

            // 更新订单状态：已退单
            Order order = new Order();
            order.setOrderNo(oe.getOrderNo());
            order.setOrderStatus("9");
            order.setLastUpdate(new Timestamp(new Date().getTime()));
            orderMapper.update(order);

            // 发票作废
            List<ExchangeOrderInvoiceBO> orderInvoiceBOList = orderExchangeRoMapper.selectInvoice(oe.getOrderNo());

            if (orderInvoiceBOList.size() > 0) {
                DzfpGetReq req = new DzfpGetReq();
                List<InvoiceXm> dataList = new ArrayList<>();
                for (ExchangeOrderInvoiceBO eoi : orderInvoiceBOList) {
                    if ("1".equals(eoi.getProperty())) { // 纸质发票
                        // todo
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
                        xm.setXmsl(1.00);
                        xm.setTotalAmt(eoi.getAmount());
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
            insertLog(oe.getOrderNo(), "7", Utils.getAdminId(), oe.getAdminRemark());
        }
        return oe;
    }

    @Transactional("db1TxManager")
    @Override
    public ResponseEntity refund(ExchangeRefundBO data) {
        if ("1".equals(data.getRefundType())) {
            OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

            // 查询交易日志中支付成功的订单
            TradeLog log = new TradeLog();
            log.setOrderNo(oe.getOrderNo());
            log.setTradeStatus("2");
            log.setPayMethod("ALIPAY");
            List<TradeLog> logList = tradeLogService.selectList(log, 1, 10000);

            if (logList.size() > 0) {
                for (int i = 0; i < logList.size(); i++) {
                    if (logList.get(i).getAmount() > data.getAmount()) {
                        AliRefund refund = new AliRefund();
                        refund.setOut_trade_no(logList.get(i).getOrderNo());
                        refund.setTrade_no(logList.get(i).getAliTrandeNo());
                        refund.setRefund_amount(String.valueOf(data.getAmount()));
                        refund.setRefund_reason(data.getAdminRemark());
                        refund.setOut_request_no(Utils.uuid());
                        ResponseEntity re = aliPayController.aliPayRefund(refund);
                        RefundRes res = JSON.parseObject(re.getBody().toString(), RefundRes.class);
                        if ("2000".equals(res.getCode())) { // 退款成功
                            oe.setStatus("8");
                            oe.setAdminRemark(data.getAdminRemark());
                            oe.setLastUpdate(new Timestamp(new Date().getTime()));
                            orderExchangeMapper.update(oe);
                            // 插入订单日志-已退款
                            insertLog(oe.getOrderNo(), "8", Utils.getAdminId(), oe.getAdminRemark());
                            // 插入订单日志-已完成
                            insertLog(oe.getOrderNo(), "4", Utils.getAdminId(), "系统自动完成");
                        }
                        return re;
                    }
                }
            }
        } else {
            throw new ServiceException(4956);
        }
        throw new ServiceException(4957);
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
        for (OrderExchange data : dataList) {
            if (data.getLastUpdate().after(DateUtil.addDays(new Date(), -Constant.ORDER_EXCHANGE_RECEIPT_DAYS))) {
                data.setStatus("4");
                data.setLastUpdate(now);
                orderExchangeMapper.update(data);

                // 插入订单日志
                insertLog(data.getOrderNo(), "4", "", "系统自动完成收货");
            }
        }
    }

    @Override
    public List<ExchangeOrderInvoiceBO> selectInvoice(String orderNo) {
        return orderExchangeRoMapper.selectInvoice(orderNo);
    }


    @Override
    public List<OrderExchange> selectList(OrderExchange oe, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return orderExchangeRoMapper.selectList(oe);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange disagree(ExchangeAdminBO data) {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("5");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "5", Utils.getAdminId(), oe.getAdminRemark());
        }
        return oe;
    }

    @Override
    public OrderExchange selectOne(String id) {
        return orderExchangeRoMapper.selectOne(id);
    }

    @Transactional("db1TxManager")
    @Override
    public OrderExchange agree(ExchangeAdminBO data) {
        OrderExchange oe = orderExchangeRoMapper.selectById(data.getId());

        if (oe != null) {
            Timestamp now = new Timestamp(new Date().getTime());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("2");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "2", Utils.getAdminId(), oe.getAdminRemark());
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
            oe.setExpressComp(data.getExpressNo());
            oe.setAdminRemark(data.getAdminRemark());
            oe.setLastUpdate(now);
            oe.setStatus("6");
            orderExchangeMapper.update(oe);

            // 插入订单日志
            insertLog(oe.getOrderNo(), "6", Utils.getAdminId(), oe.getAdminRemark());
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

    private void insertLog(String orderNo, String status, String userId, String remark) {
        // 插入订单日志
        OrderLog ol = new OrderLog.Builder()
                .id(Utils.uuid())
                .orderNo(orderNo)
                .action(selectFieldValue("exchange_status", status))
                .createTime(new Timestamp(new Date().getTime()))
                .createUser(userId)
                .remark(remark)
                .build();
        orderLogMapper.insert(ol);
    }
}
