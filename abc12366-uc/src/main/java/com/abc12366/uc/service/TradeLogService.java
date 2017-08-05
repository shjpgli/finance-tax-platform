package com.abc12366.uc.service;

import com.abc12366.uc.model.TradeLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeLogService {

    /**
     * 查询交易日志列表信息
     *
     * @param tradeLog
     * @return
     */
    List<TradeLog> selectList(TradeLog tradeLog);

}
