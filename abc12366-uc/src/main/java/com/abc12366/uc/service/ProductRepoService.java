package com.abc12366.uc.service;

import com.abc12366.uc.model.ProductRepo;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.model.bo.ProductRepoBO;

import java.util.List;

/**
 * 库存Service
 *
 * @author lizhongwei
 * @create 2017-07-01
 * @since 1.0.0
 */
public interface ProductRepoService {

    /**
     * 查询库存列表信息
     * @param productRepoBO
     * @return
     */
    List<ProductRepoBO> selectList(ProductRepoBO productRepoBO);

    /**
     * 查询库存信息
     * @param id
     * @return
     */
    ProductRepoBO selectOne(String id);

    /**
     * 库存入库
     * @param productRepoBO
     * @return
     */
    ProductRepoBO income(ProductRepoBO productRepoBO);

    /**
     * 库存出库
     * @param productRepoBO
     * @return
     */
    ProductRepoBO outcome(ProductRepoBO productRepoBO);

    List<ProductRepoBO> selectProductRepoDetail(ProductRepo productRepo);
}
