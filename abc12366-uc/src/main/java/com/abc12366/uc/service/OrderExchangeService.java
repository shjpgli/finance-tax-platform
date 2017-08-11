package com.abc12366.uc.service;

import com.abc12366.uc.model.OrderExchange;
import com.abc12366.uc.model.bo.*;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 10:27 AM
 * @since 1.0.0
 */
public interface OrderExchangeService {
    OrderExchange insert(ExchangeApplicationBO data);

    List<OrderExchange> selectList(OrderExchange oe, int pageNum, int pageSize);

    OrderExchange disagree(ExchangeAdminBO data);

    OrderExchange selectOne(String id);

    OrderExchange agree(ExchangeAdminBO data);

    OrderExchange confirm(ExchangeConfirmBO data);

    OrderExchange update(ExchangeApplicationBO data);

    List<SfExportBO> export(OrderExchangeExportBO data);

    void importJson(List<SfImportBO> dataList);
}
