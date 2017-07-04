package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.ProductRepoMapper;
import com.abc12366.uc.mapper.db2.ProductRepoRoMapper;
import com.abc12366.uc.model.ProductRepo;
import com.abc12366.uc.model.bo.ProductRepoBO;
import com.abc12366.uc.service.ProductRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<ProductRepoBO> selectList(ProductRepoBO productRepoBO) {
        String type = productRepoBO.getType();
        if(type != null && !"".equals(type)){
            if(type.equals("0")){
                //无货 0
                productRepoBO.setOption(0);
            }else if(type.contains("<")){
                //小于 1
                productRepoBO.setOption(1);
            }else if(type.contains(">")){
                //大于 2
                productRepoBO.setOption(2);
            }else if(type.contains("-")){
                //取中间值
                productRepoBO.setOption(3);
            }
        }
        return productRepoRoMapper.selectList(productRepoBO);
    }

    @Override
    public ProductRepoBO selectOne(String id) {
        return productRepoRoMapper.selectOne(id);
    }

    @Override
    public ProductRepoBO income(ProductRepoBO productRepoBO) {
        productRepoBO.setId(Utils.uuid());
        int stock = 0;
        ProductRepo temp = productRepoRoMapper.selectByGoodsId(productRepoBO);
        stock = temp.getStock() + productRepoBO.getIncome();
        productRepoBO.setStock(stock);
        ProductRepo productRepo = new ProductRepo();
        BeanUtils.copyProperties(productRepoBO, productRepo);
        int insert = productRepoMapper.insert(productRepo);
        if(insert != 1){
            LOGGER.info("{商品入库失败}", productRepo);
            throw new ServiceException(4158);
        }
        return productRepoBO;
    }


    @Transactional("db1TxManager")
    @Override
    public ProductRepoBO outcome(ProductRepoBO productRepoBO) {
        productRepoBO.setId(Utils.uuid());
        int stock = 0;
        ProductRepo temp = productRepoRoMapper.selectByGoodsId(productRepoBO);
        stock = temp.getStock() - productRepoBO.getOutcome();
        productRepoBO.setStock(stock);
        ProductRepo productRepo = new ProductRepo();
        BeanUtils.copyProperties(productRepoBO, productRepo);
        int insert = productRepoMapper.insert(productRepo);
        if(insert != 1){
            LOGGER.info("{商品出库失败}", productRepo);
            throw new ServiceException(4159);
        }
        return productRepoBO;
    }
}
