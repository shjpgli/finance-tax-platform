package com.abc12366.uc.service;

import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.ProductBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface ProductService {

    List<Product> selectList(Product product);

    ProductBO selectOne(String id);

    ProductBO add(ProductBO productBO);

    ProductBO update(ProductBO productBO);


    List<Product> selectProductList(Product product);

    ProductBO selectProduct(String id);

    void updateStatus(String id);
}
