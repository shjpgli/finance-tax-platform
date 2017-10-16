package com.abc12366.uc.service;

import com.abc12366.uc.model.UvipPrice;
import com.abc12366.uc.model.bo.UvipPriceBO;

import java.util.List;

/**
 * Created by MY on 2017-07-04.
 */
public interface UvipPriceService {

    List<UvipPriceBO> selectList(UvipPrice uvipPrice);

}
