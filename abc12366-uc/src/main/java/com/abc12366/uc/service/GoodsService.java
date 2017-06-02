package com.abc12366.uc.service;

import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.bo.GoodsBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface GoodsService {

    List<Goods> selectList(Goods goods);

    GoodsBO selectOne(String id);

    GoodsBO add(GoodsBO goodsBO);

    GoodsBO update(GoodsBO goodsBO);


    List<Goods> selectGoodsList(Goods goods);

    GoodsBO selectGoods(String id);

    void updateStatus(String id);
}
