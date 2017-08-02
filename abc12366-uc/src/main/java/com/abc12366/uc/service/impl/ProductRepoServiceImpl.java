package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.ProductMapper;
import com.abc12366.uc.mapper.db1.ProductRepoMapper;
import com.abc12366.uc.mapper.db2.ProductRepoRoMapper;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.ProductRepo;
import com.abc12366.uc.model.bo.ProductRepoBO;
import com.abc12366.uc.service.ProductRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lizhongwei
 * @create 2017-07-01
 * @since 1.0.0
 */
@Service
public class ProductRepoServiceImpl implements ProductRepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepoServiceImpl.class);

    @Autowired
    private ProductRepoRoMapper productRepoRoMapper;

    @Autowired
    private ProductRepoMapper productRepoMapper;

    @Autowired
    private ProductMapper productMapper;



    @Override
    public List<ProductRepoBO> selectList(ProductRepoBO productRepoBO) {
        return productRepoRoMapper.selectList(productRepoBO);
    }

    @Override
    public ProductRepoBO selectById(String id) {
        return productRepoRoMapper.selectById(id);
    }

    @Override
    public ProductRepoBO income(ProductRepoBO productRepoBO) {
        productRepoBO.setId(Utils.uuid());
        int stock = 0;
        ProductRepoBO temp = productRepoRoMapper.selectByGoodsId(productRepoBO);
        if(temp == null){
            stock = productRepoBO.getIncome();
        }else{
            stock = temp.getStock() + productRepoBO.getIncome();
        }
        Date date = new Date();
        productRepoBO.setCreateTime(date);
        productRepoBO.setLastUpdate(date);
        productRepoBO.setStock(stock);
        ProductRepo productRepo = new ProductRepo();
        BeanUtils.copyProperties(productRepoBO, productRepo);
        int insert = productRepoMapper.insert(productRepo);
        if(insert != 1){
            LOGGER.info("商品入库失败:{}", productRepo);
            throw new ServiceException(4158);
        }
        //入库成功后，修改产品表的库存信息
        updateProductStock(productRepoBO, stock);
        return productRepoBO;
    }


    @Transactional("db1TxManager")
    @Override
    public ProductRepoBO outcome(ProductRepoBO productRepoBO) {
        productRepoBO.setId(Utils.uuid());
        int stock = 0;
        ProductRepoBO temp = productRepoRoMapper.selectByGoodsId(productRepoBO);
        if(temp == null){
            stock = productRepoBO.getIncome();
        }else{
            stock = temp.getStock() - productRepoBO.getOutcome();
        }
        Date date = new Date();
        productRepoBO.setCreateTime(date);
        productRepoBO.setLastUpdate(date);
        productRepoBO.setStock(stock);
        ProductRepo productRepo = new ProductRepo();
        BeanUtils.copyProperties(productRepoBO, productRepo);
        int insert = productRepoMapper.insert(productRepo);
        if(insert != 1){
            LOGGER.info("商品出库失败:{}", productRepo);
            throw new ServiceException(4159);
        }
        //出库成功后，修改产品表的库存信息
        updateProductStock(productRepoBO, stock);
        return productRepoBO;
    }

    @Override
    public List<ProductRepoBO> selectProductRepoDetail(ProductRepo productRepo) {
        return productRepoRoMapper.selectProductRepoDetail(productRepo);
    }

    private void updateProductStock(ProductRepoBO productRepoBO, int stock) {
        Product product = new Product();
        product.setGoodsId(productRepoBO.getGoodsId());
        product.setId(productRepoBO.getProductId());
        product.setStock(stock);
        int pUpdate = productMapper.update(product);
        if (pUpdate != 1){
            LOGGER.info("产品库存修改失败:{}", product);
            throw new ServiceException(4164);
        }
    }
}
