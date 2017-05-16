package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.Product;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ProductMapper数据增删改库操作接口类
 * 
 **/

public interface ProductMapper{


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
	int insert(Product record);


	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int update(Product record);


}