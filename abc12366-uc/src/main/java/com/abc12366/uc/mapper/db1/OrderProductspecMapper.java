package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.OrderProductSpec;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * OrderProductspecMapper数据库操作接口类
 * 
 **/

public interface OrderProductSpecMapper {



	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int delete(@Param("id") String id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(OrderProductSpec record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int update(OrderProductSpec record);

}