package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db1.TradeLogMapper;
import com.abc12366.uc.mapper.db2.TradeLogRoMapper;
import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.model.bo.TradeBillBO;
import com.abc12366.uc.service.TradeLogService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    public List<TradeLog> selectList(TradeLog tradeLog, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return tradeLogRoMapper.selectList(tradeLog);
    }

	public int insertTradeLog(TradeLog tradeLog) {
		return tradeLogMapper.insertTradeLog(tradeLog);
	}

    public TradeLog selectOne(String tradeNo) {
        return tradeLogRoMapper.selectOne(tradeNo);
    }

    @Transactional("db1TxManager")
    @Override
    public List<TradeBillBO> bill(List<TradeBillBO> dataList) {
        List<TradeBillBO> undoneList = new ArrayList<>();
        if (dataList.size() > 0) {
            for (TradeBillBO data: dataList) {
                TradeLog log = selectOne(data.getTradeNo());
                if (log.getOrderNo().equals(data.getOrderNo())
                        && log.getAmount() == data.getAmount()) {
                    log.setCompareStatus("1");
                    log.setCompareTime(new Date());
                    tradeLogMapper.update(log);
                } else {
                    undoneList.add(data);
                }
            }
        }
        return undoneList;
    }

    @Override
    public TradeLog update(TradeLog log) {
        TradeLog tradeLog = tradeLogRoMapper.selectByPrimaryKey(log.getId());
        tradeLog.setCompareStatus(log.getCompareStatus());
        tradeLog.setCompareTime(new Date());
        tradeLogMapper.update(tradeLog);
        return tradeLog;
    }
}
