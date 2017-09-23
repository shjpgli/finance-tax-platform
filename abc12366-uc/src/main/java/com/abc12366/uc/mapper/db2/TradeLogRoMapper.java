package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.TradeLog;
import com.abc12366.uc.model.bo.TradeBillBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TradeLogMapper数据库操作接口类
 **/

public interface TradeLogRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    TradeLog selectByPrimaryKey(@Param("id") String id);

    List<TradeLog> selectList(TradeLog tradeLog);

    TradeLog selectOne(TradeBillBO data);
}