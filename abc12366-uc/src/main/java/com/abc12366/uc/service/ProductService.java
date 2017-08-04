package com.abc12366.uc.service;

import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.DictBO;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.ProductBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface ProductService {

    List<Product> selectList(Product product);

    ProductBO add(ProductBO productBO);

    ProductBO update(ProductBO productBO);

    List<ProductBO> selectByGoodsId(ProductBO product);

    List<GoodsBO> selectBOList(ProductBO productBO);

    List<DictBO> selectSpecByGoodsId(ProductBO product);
}
