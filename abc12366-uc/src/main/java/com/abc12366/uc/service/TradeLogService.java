package com.abc12366.uc.service;

import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.model.bo.TradeBillBO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeLogService {

    /**
     * 查询交易日志列表信息
     */
    List<TradeLog> selectList(TradeLog tradeLog, int page, int size);
    
    int insertTradeLog(TradeLog tradeLog);

    List<TradeBillBO> bill(List<TradeBillBO> dataList);

    TradeLog updateCompare(TradeLog log);

    TradeLog selectOne(TradeBillBO log);

    void update(TradeLog tradeLogUpdate);
}
