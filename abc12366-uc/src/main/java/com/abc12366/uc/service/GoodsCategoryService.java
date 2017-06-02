package com.abc12366.uc.service;

import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.bo.GoodsCategoryBO;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
public interface GoodsCategoryService {

    List<GoodsCategoryBO> selectList(GoodsCategory goodsCategory);

    GoodsCategory add(GoodsCategoryBO goodsCategoryBO);

    GoodsCategoryBO update(GoodsCategoryBO goodsCategoryBO);

    GoodsCategory selectGategory(String id);

    void delete(String id);
}
