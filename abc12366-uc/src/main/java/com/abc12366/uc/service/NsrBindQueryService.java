package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.*;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:45
 */
public interface NsrBindQueryService {
    List<NsrBindQueryBO> selectList(NsrBindQueryParamBO nsrBindQueryParamBO);

    UserDzsbBO selectDzsb(String id);

    UserHndsBO selectHnds(String id);

    UserHngsBO selectHngs(String id);
}
