package com.abc12366.uc.service;

import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
public interface OrderExchangeService {

    /**
     * 新增退换货申请
     */
    OrderExchange insert(ExchangeApplicationBO data);

    /**
     * 查询退换货申请列表
     */
    List<OrderExchange> selectList(OrderExchange oe, int pageNum, int pageSize);

    /**
     * 查询退换货申请列表for财务
     */
    List<OrderExchange> selectListForFinance(OrderExchange oe, int pageNum, int pageSize);

    /**
     * 拒绝退换货申请
     */
    OrderExchange disagree(ExchangeAdminBO data);

    /**
     * 查看退换货申请详情
     */
    OrderExchange selectOne(String id);

    /**
     * 同意退换货申请
     */
    OrderExchange agree(ExchangeAdminBO data);

    /**
     * 换货申请确认收货
     */
    OrderExchange confirm(ExchangeConfirmBO data);

    /**
     * 更新退换货申请
     */
    OrderExchange update(ExchangeApplicationBO data);

    /**
     * 导出换货申请中需要发货的数据（顺丰模版）
     */
    List<SfExportBO> export(OrderExchangeExportBO data);

    /**
     * 导入快递单号（顺丰模版）
     */
    void importJson(List<SfImportBO> dataList);

    /**
     * 退单申请中的确认退货，退票
     */
    OrderExchange back(ExchangeBackBO data) throws Exception;

    /**
     * 退单申请中的退款、退积分处理
     */
    ResponseEntity refund(ExchangeRefundBO data);

    /**
     * 换货申请自动收货处理
     */
    void automaticReceipt() throws Exception;

    /**
     * 根据订单号查询发票信息
     */
    List<ExchangeOrderInvoiceBO> selectInvoice(String orderNo);
}
