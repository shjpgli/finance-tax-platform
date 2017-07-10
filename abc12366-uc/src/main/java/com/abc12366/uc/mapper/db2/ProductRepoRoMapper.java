package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.ProductRepo;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.model.bo.ProductRepoBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

	List<ProductRepoBO> selectList(ProductRepoBO productRepoBO);

	ProductRepoBO selectOne(String id);

	ProductRepo selectByGoodsId(ProductRepoBO productRepoBO);

	List<ProductRepoBO> selectProductRepoDetail(ProductBO productBO);
}