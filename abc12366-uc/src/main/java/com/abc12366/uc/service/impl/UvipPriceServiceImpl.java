package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db2.UvipPriceRoMapper;
import com.abc12366.uc.model.UvipPrice;
import com.abc12366.uc.service.UvipPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @since 1.0.0
 */
@Service
public class UvipPriceServiceImpl implements UvipPriceService {

    @Autowired
    private UvipPriceRoMapper uvipPriceRoMapper;

    @Override
    public List<UvipPrice> selectList(UvipPrice uvipPrice) {
        return uvipPriceRoMapper.selectList(uvipPrice);
    }

}
