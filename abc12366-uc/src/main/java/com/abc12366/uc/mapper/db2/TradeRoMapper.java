package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.order.Trade;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * TradeMapper数据库操作接口类
 * 
 **/

public interface TradeRoMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Trade  selectById(@Param("id") String id);
}