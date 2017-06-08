package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.GoodsMapper;
import com.abc12366.uc.mapper.db1.ProductMapper;
import com.abc12366.uc.mapper.db2.GoodsCategoryRoMapper;
import com.abc12366.uc.mapper.db2.GoodsRoMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.Product;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.GoodsCheckBO;
import com.abc12366.uc.model.bo.ProductBO;
import com.abc12366.uc.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsRoMapper goodsRoMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductRoMapper productRoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<GoodsBO> selectList(Goods goods) {
        return goodsRoMapper.selectList(goods);
    }

    @Override
    public GoodsBO selectOne(String id) {
        return null;
    }

    @Transactional("db1TxManager")
    @Override
    public GoodsBO add(GoodsBO goodsBO) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsBO,goods);
        //新增商品信息
        String goodsId = Utils.uuid();
        goods.setId(goodsId);
        Date date = new Date();
        goods.setCreateTime(date);
        goods.setLastUpdate(date);
        int gInsert = goodsMapper.insert(goods);
        if(gInsert != 1){
            LOGGER.info("{新增产品失败}", goods);
            throw new ServiceException(4101);
        }
        GoodsBO bo = new GoodsBO();
        BeanUtils.copyProperties(goods,bo);
        //加入产品参数
        List<ProductBO> productList = goodsBO.getProductBOList();
        List<ProductBO> productBOs = new ArrayList<ProductBO>();
        Product product = null;
        ProductBO productBO = null;
        int pInsert = 0;
        for (ProductBO pBO:productList){
            product = new Product();
            BeanUtils.copyProperties(pBO,product);
            product.setGoodsId(goodsId);
            product.setCreateTime(date);
            product.setLastUpdate(date);
            pInsert = productMapper.insert(product);
            if(pInsert != 1){
                LOGGER.info("{新增产品参数失败}", product);
                throw new ServiceException(4101);
            }
            productBO = new ProductBO();
            BeanUtils.copyProperties(product,productBO);
            productBOs.add(productBO);
        }
        bo.setProductBOList(productBOs);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public GoodsBO update(GoodsBO goodsBO) {
        String goodsId = goodsBO.getId();
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsBO,goods);
        //新增商品信息
        Date date = new Date();
        goods.setLastUpdate(date);
        int gUpdate = goodsMapper.update(goods);
        if(gUpdate != 1){
            LOGGER.info("{修改产品失败}", goods);
            throw new ServiceException(4102);
        }
        GoodsBO bo = new GoodsBO();
        BeanUtils.copyProperties(goods,bo);
        //先删除该产品所有产品参数
        //List<Product> pList = productRoMapper.selectById(goodsId);
        productMapper.deleteByGoodsId(goodsId);

        //加入产品参数
        List<ProductBO> productList = goodsBO.getProductBOList();
        List<ProductBO> productBOs = new ArrayList<ProductBO>();
        Product product = null;
        ProductBO productBO = null;
        int pInsert = 0;
        for (ProductBO pBO:productList){
            product = new Product();
            BeanUtils.copyProperties(pBO,product);
            product.setGoodsId(goodsId);
            product.setLastUpdate(date);
            pInsert = productMapper.insert(product);
            if(pInsert != 1){
                LOGGER.info("{修改产品参数失败}", product);
                throw new ServiceException(4102);
            }
            productBO = new ProductBO();
            BeanUtils.copyProperties(product,productBO);
            productBOs.add(productBO);
        }
        bo.setProductBOList(productBOs);
        return bo;
    }

    @Override
    public List<Goods> selectGoodsList(Goods goods) {
        return null;
    }

    @Override
    public GoodsBO selectGoods(String id) {
        return goodsRoMapper.selectGoods(id);
    }

    @Transactional("db1TxManager")
    @Override
    public void checkGoods(GoodsCheckBO goodsCheckBO) {
        String goodsIds = goodsCheckBO.getGoodsIds();
        String[] ids = goodsIds.split(",");
        Goods goods = null;
        int upd = 0;
        for (String goodsId : ids){
            goods = new Goods();
            goods.setId(goodsId);
            goods.setStatus(goodsCheckBO.getStatus());
            upd = goodsMapper.update(goods);
            if(upd != 1){
                LOGGER.info("{修改产品参数失败}", goods);
                throw new ServiceException(4119);
            }
        }
    }
}
