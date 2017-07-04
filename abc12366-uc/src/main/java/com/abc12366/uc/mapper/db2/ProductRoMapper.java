package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.ProductBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * ProductMapper数据库查询操作接口类
 * 
 **/

public interface ProductRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
    List<Product> selectById(@Param("id") String id);


    List<Product> selectList(Product product);

    List<Product> selectProductList(Product product);

    Product selectByIdAndStatus(String id);

    Product selectProduct(Product product);

    List<Product> selectByGoodsId(@Param("goodsId")String goodsId);

    List<ProductBO> selectBOListByGoodsId(String goodsId);
}