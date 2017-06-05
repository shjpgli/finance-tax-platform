package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.ProductMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRoMapper productRoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> selectList(Product product) {
        return productRoMapper.selectList(product);
    }

    @Override
    public List<ProductBO> selectProductBOList(String id) {
        List<Product> productList = productRoMapper.selectById(id);
        ProductBO productBO = null;
        List<ProductBO> bo = new ArrayList<ProductBO>();
        for(Product p:productList){
            productBO = new ProductBO();
            BeanUtils.copyProperties(p, productBO);
            bo.add(productBO);

        }
        return bo;
    }

    @Override
    public ProductBO add(ProductBO productBO) {
        Product product = new Product();
        BeanUtils.copyProperties(productBO, product);
//        product.setId(Utils.uuid());
        Date date = new Date();
        product.setCreateTime(date);
        product.setLastUpdate(date);
        int insert = productMapper.insert(product);
        if (insert != 1) {
            throw new ServiceException(4101);
        } else {
            ProductBO temp = new ProductBO();
            BeanUtils.copyProperties(product, temp);
            return temp;
        }
    }

    @Override
    public ProductBO update(ProductBO productBO) {
        Product product = new Product();
        BeanUtils.copyProperties(productBO, product);

        Date date = new Date();
        product.setLastUpdate(date);
        int update = productMapper.update(product);
        if (update != 1) {
            throw new ServiceException(4102);
        } else {
            ProductBO temp = new ProductBO();
            BeanUtils.copyProperties(product, temp);
            return temp;
        }
    }

    @Override
    public List<Product> selectProductList(Product product) {
        return productRoMapper.selectProductList(product);
    }

    @Override
    public ProductBO selectProduct(String id) {
        Product product = productRoMapper.selectByIdAndStatus(id);
        ProductBO productBO = new ProductBO();
        BeanUtils.copyProperties(product, productBO);
        return productBO;
    }

    @Override
    public void updateStatus(String id) {
        productMapper.updateStatus(id);
    }
}
