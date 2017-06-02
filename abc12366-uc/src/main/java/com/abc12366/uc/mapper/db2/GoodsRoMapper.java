package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Goods;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * GoodsMapper数据库操作接口类
 * 
 **/

public interface GoodsRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Goods  selectByPrimaryKey(@Param("id") String id);

}