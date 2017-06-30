package com.abc12366.uc.service;

import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.bo.GoodsCategoryBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MY on 2017-05-15.
 */
@Service
public interface GoodsCategoryService {

    GoodsCategoryBO selectList(GoodsCategory goodsCategory);

    GoodsCategory add(GoodsCategoryBO goodsCategoryBO);

    GoodsCategoryBO update(GoodsCategoryBO goodsCategoryBO);

    GoodsCategory selectGategory(String id);

    void delete(String id);
}
