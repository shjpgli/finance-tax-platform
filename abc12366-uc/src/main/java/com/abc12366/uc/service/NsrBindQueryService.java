package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.NsrBindQueryBO;
import com.abc12366.uc.model.bo.NsrBindQueryParamBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:45
 */
public interface NsrBindQueryService {
    List<NsrBindQueryBO> selectList(NsrBindQueryParamBO nsrBindQueryParamBO);

    List<NsrBindQueryBO> selectDzsb();
}
