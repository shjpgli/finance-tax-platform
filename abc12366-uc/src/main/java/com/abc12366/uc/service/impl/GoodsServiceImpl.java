package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.GoodsMapper;
import com.abc12366.uc.mapper.db1.ProductMapper;
import com.abc12366.uc.mapper.db1.ProductSpecMapper;
import com.abc12366.uc.mapper.db1.UvipPriceMapper;
import com.abc12366.uc.mapper.db2.GoodsRoMapper;
import com.abc12366.uc.mapper.db2.OrderProductRoMapper;
import com.abc12366.uc.mapper.db2.ProductRoMapper;
import com.abc12366.uc.model.*;
import com.abc12366.uc.model.bo.DictBO;
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

    @Autowired
    private OrderProductRoMapper orderProductRoMapper;

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Autowired
    private UvipPriceMapper uvipPriceMapper;


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
        BeanUtils.copyProperties(goodsBO, goods);
        //新增商品信息
        String goodsId = Utils.uuid();
        goods.setId(goodsId);
        Date date = new Date();
        goods.setCreateTime(date);
        goods.setLastUpdate(date);
        int gInsert = goodsMapper.insert(goods);
        if (gInsert != 1) {
            LOGGER.info("{新增产品失败}", goods);
            throw new ServiceException(4101);
        }
        GoodsBO bo = new GoodsBO();
        BeanUtils.copyProperties(goods, bo);
        //加入产品参数
        List<ProductBO> productList = goodsBO.getProductBOList();
        List<ProductBO> productBOs = new ArrayList<ProductBO>();
        int pInsert = 0;
        for (ProductBO pBO : productList) {
            ProductBO productBO = new ProductBO();
            Product product = new Product();
            BeanUtils.copyProperties(pBO, product);
            String productId = Utils.uuid();
            product.setId(productId);
            product.setGoodsId(goodsId);
            product.setCreateTime(date);
            product.setLastUpdate(date);
            //新增产品参数
            pInsert = productMapper.insert(product);
            if (pInsert != 1) {
                LOGGER.info("{新增产品参数失败}", product);
                throw new ServiceException(4101);
            }
            BeanUtils.copyProperties(product, productBO);
            //新增规格信息与产品对应关系
            List<DictBO> dictList = pBO.getDictList();
            for (DictBO dictBO : dictList) {
                ProductSpec productSpec = new ProductSpec();
                productSpec.setId(Utils.uuid());
                productSpec.setProductId(productId);
                productSpec.setSpecId(dictBO.getId());
                int sInsert = productSpecMapper.insert(productSpec);
                if (sInsert != 1) {
                    LOGGER.info("{新增产品规格信息失败}", productSpec);
                    throw new ServiceException(4160);
                }
            }
            //新增产品会员价格
            List<UvipPrice> uvipPriceList = pBO.getUvipPriceList();
            List<UvipPrice> uvipList = new ArrayList<>();
            for (UvipPrice uvipPrice : uvipPriceList) {
                String uvipPriceId = Utils.uuid();
                uvipPrice.setId(uvipPriceId);
                uvipPrice.setProductId(productId);
                uvipPrice.setCreateTime(date);
                uvipPrice.setLastUpdate(date);
                int sInsert = uvipPriceMapper.insert(uvipPrice);
                if (sInsert != 1) {
                    LOGGER.info("{新增产品单个规格会员价格失败}", uvipPrice);
                    throw new ServiceException(4161);
                }
                uvipList.add(uvipPrice);
            }
            productBO.setUvipPriceList(uvipPriceList);
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
        BeanUtils.copyProperties(goodsBO, goods);
        //先判断商品是否有被卖出，被卖出不能修改
        List<Product> pBOList = productRoMapper.selectByGoodsId(goodsBO.getId());
        for (Product product:pBOList){
            OrderProduct orderProduct = orderProductRoMapper.selectByProductId(product.getId());
            if(orderProduct != null){
                LOGGER.info("{商品有被卖出，被卖出不能修改}", product);
                throw new ServiceException(4162);
            }
        }
        //修改产品信息
        Date date = new Date();
        goods.setLastUpdate(date);
        int gUpdate = goodsMapper.update(goods);
        if (gUpdate != 1) {
            LOGGER.info("{修改产品失败}", goods);
            throw new ServiceException(4102);
        }
        GoodsBO bo = new GoodsBO();
        BeanUtils.copyProperties(goods, bo);
        //先删除该产品所有产品参数
        productMapper.deleteByGoodsId(goodsId);

        //加入产品参数
        List<ProductBO> productList = goodsBO.getProductBOList();
        List<ProductBO> productBOs = new ArrayList<ProductBO>();
        int pInsert = 0;
        for (ProductBO pBO : productList) {
            ProductBO productBO = new ProductBO();
            Product product = new Product();
            BeanUtils.copyProperties(pBO, product);
            String productId = Utils.uuid();
            product.setId(productId);
            product.setGoodsId(goodsId);
            product.setCreateTime(date);
            product.setLastUpdate(date);
            //新增产品参数
            pInsert = productMapper.insert(product);
            if (pInsert != 1) {
                LOGGER.info("{新增产品参数失败}", product);
                throw new ServiceException(4101);
            }
            BeanUtils.copyProperties(product, productBO);
            //新增规格信息与产品对应关系
            List<DictBO> dictList = pBO.getDictList();
            for (DictBO dictBO : dictList) {
                ProductSpec productSpec = new ProductSpec();
                productSpec.setId(Utils.uuid());
                productSpec.setProductId(productId);
                productSpec.setSpecId(dictBO.getId());
                int sInsert = productSpecMapper.insert(productSpec);
                if (sInsert != 1) {
                    LOGGER.info("{新增产品规格信息失败}", productSpec);
                    throw new ServiceException(4160);
                }
            }
            //新增产品会员价格
            List<UvipPrice> uvipPriceList = pBO.getUvipPriceList();
            List<UvipPrice> uvipList = new ArrayList<>();
            for (UvipPrice uvipPrice : uvipPriceList) {
                String uvipPriceId = Utils.uuid();
                uvipPrice.setId(uvipPriceId);
                uvipPrice.setProductId(productId);
                uvipPrice.setCreateTime(date);
                uvipPrice.setLastUpdate(date);
                int sInsert = uvipPriceMapper.insert(uvipPrice);
                if (sInsert != 1) {
                    LOGGER.info("{新增产品单个规格会员价格失败}", uvipPrice);
                    throw new ServiceException(4161);
                }
                uvipList.add(uvipPrice);
            }
            productBO.setUvipPriceList(uvipPriceList);
            productBOs.add(productBO);
        }
        bo.setProductBOList(productBOs);
        return bo;
    }

    @Override
    public List<Goods> selectGoodsList(Goods goods) {
        return goodsRoMapper.selectGoodsList(goods);
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
        for (String goodsId : ids) {
            goods = new Goods();
            goods.setId(goodsId);
            goods.setStatus(goodsCheckBO.getStatus());
            upd = goodsMapper.update(goods);
            if (upd != 1) {
                LOGGER.info("{修改产品参数失败}", goods);
                throw new ServiceException(4119);
            }
        }
    }

    @Override
    public void deleteGoods(String id) {
        //先判断商品是否有被卖出，被卖出不能删除
        List<Product> pBOList = productRoMapper.selectByGoodsId(id);
        for (Product prod:pBOList){
            OrderProduct orderProduct = orderProductRoMapper.selectByProductId(id);
            if(orderProduct != null){
                LOGGER.info("{商品有被卖出，被卖出不能修改}", prod);
                throw new ServiceException(4163);
            }
        }
        //删除产品信息
        int gDelete = goodsMapper.deleteByPrimaryKey(id);
        if (gDelete != 1) {
            LOGGER.info("{删除产品失败}", id);
            throw new ServiceException(4103);
        }
    }
}
