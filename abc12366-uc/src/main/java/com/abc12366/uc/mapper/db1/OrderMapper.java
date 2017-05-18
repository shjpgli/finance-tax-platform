package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * OrderMapper数据库操作接口类
 * 
 **/

public interface OrderMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByIdAndUserId(Order order);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(Order record);


	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int update(Order record);

}