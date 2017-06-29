package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.ProductRepo;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ProductRepoMapper数据库操作接口类
 * 
 **/

public interface ProductRepoRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ProductRepo  selectByPrimaryKey(@Param("id") String id);

}