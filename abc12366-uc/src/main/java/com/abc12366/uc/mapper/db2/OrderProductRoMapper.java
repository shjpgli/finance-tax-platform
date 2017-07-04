package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.OrderProduct;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * OrderProductMapper数据库操作接口类
 * 
 **/

public interface OrderProductRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	OrderProduct  selectByPrimaryKey(@Param("id") String id);


	OrderProduct selectByProductId(@Param("productId") String productId );
}