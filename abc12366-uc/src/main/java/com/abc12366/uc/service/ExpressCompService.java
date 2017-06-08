package com.abc12366.uc.service;

import com.abc12366.uc.model.ExpressComp;
import com.abc12366.uc.model.bo.ExpressCompBO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpressCompService {

    List<ExpressComp> selectList(ExpressComp expressComp);

    ExpressCompBO add(ExpressCompBO expressCompBO);

    ExpressCompBO update(ExpressCompBO expressCompBO);

    ExpressComp selectExpressComp(String id);

    void delete(String id);
}
