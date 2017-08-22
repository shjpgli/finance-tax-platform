package com.abc12366.uc.service;

import com.abc12366.uc.model.Goods;
import com.abc12366.uc.model.bo.GoodsBO;
import com.abc12366.uc.model.bo.GoodsCheckBO;
import com.abc12366.uc.model.bo.GoodsLogBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
@Service
public interface GoodsService {

    List<GoodsBO> selectList(Goods goods);

    GoodsBO selectOne(String id);

    GoodsBO add(GoodsBO goodsBO);

    GoodsBO update(GoodsBO goodsBO);


    List<Goods> selectGoodsList(Goods goods);

    GoodsBO selectGoods(String id);

    void checkGoods(GoodsCheckBO goodsCheckBO);

    void deleteGoods(String id);

    List<GoodsBO> selectProductRepoList(GoodsBO goods);

    GoodsBO selectUserGoods(String id);

    List<GoodsBO> selectGoodsBOList(Goods goods);

    List<GoodsLogBO> selectGoodsLogList(String id);
}
