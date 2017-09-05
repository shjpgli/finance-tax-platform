package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.ExchangeCompletedOrderBO;
import com.abc12366.uc.model.bo.ExchangeOrderInvoiceBO;
import com.abc12366.uc.model.bo.OrderExchangeExportBO;
import com.abc12366.uc.model.bo.SfExportBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 11:02 AM
 * @since 1.0.0
 */
public interface OrderExchangeRoMapper {
    List<OrderExchange> selectList(OrderExchange oe);

    List<OrderExchange> selectListForFinance(OrderExchange oe);

    List<OrderExchange> selectUndoneList(String orderNo);

    OrderExchange selectOne(String id);

    List<SfExportBO> export(OrderExchangeExportBO data);

    ExchangeCompletedOrderBO selectCompletedOrder(String orderNo);

    List<ExchangeOrderInvoiceBO> selectOrderInvoice(String orderNo);

    List<ExchangeOrderInvoiceBO> selectInvoice(String orderNo);

    OrderExchange selectById(String id);

    OrderExchange selectByOrderNo(String orderNo);
}
