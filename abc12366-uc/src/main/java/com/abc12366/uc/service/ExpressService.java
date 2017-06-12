package com.abc12366.uc.service;

import com.abc12366.uc.model.Express;
import com.abc12366.uc.model.bo.ExpressBO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpressService {

    List<ExpressBO> selectList(ExpressBO expressBO);

    ExpressBO send(Express express);

    ExpressBO update(ExpressBO expressBO);

    Express selectExpress(String id);

    void delete(String id);

    ExpressBO importExpress(ExpressBO expressBO);
}
