package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db1.TradeLogMapper;
import com.abc12366.uc.mapper.db2.TradeLogRoMapper;
import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.service.TradeLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @since 1.0.0
 */
@Service
public class TradeLogServiceImpl implements TradeLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeLogServiceImpl.class);

    @Autowired
    private TradeLogRoMapper tradeLogRoMapper;
    
    @Autowired
    private TradeLogMapper tradeLogMapper;

    @Override
    public List<TradeLog> selectList(TradeLog tradeLog) {
        return tradeLogRoMapper.selectList(tradeLog);
    }

    @Transactional("db1TxManager")
	public int insertTradeLog(TradeLog tradeLog) {
		return tradeLogMapper.insertTradeLog(tradeLog);
	}
}
