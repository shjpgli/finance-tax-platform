package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.NsrBindQueryBO;
import com.abc12366.uc.model.bo.NsrBindQueryParamBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 14:45
 */
public interface NsrBindQueryRoMapper {
    List<NsrBindQueryBO> selectList(NsrBindQueryParamBO nsrBindQueryParamBO);

    List<NsrBindQueryBO> selectDzsbBindList(NsrBindQueryParamBO nsrBindQueryParamBO);

    List<NsrBindQueryBO> selectHndsBindList(NsrBindQueryParamBO nsrBindQueryParamBO);

    List<NsrBindQueryBO> selectHngsBindList(NsrBindQueryParamBO nsrBindQueryParamBO);
}
