package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.NsrBO;
import com.abc12366.uc.model.bo.NsrSelectBO;
import com.abc12366.uc.model.bo.NsrUpdateBO;

import java.util.List;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
public interface NsrService {
    NsrBO selectOne(String id);

    List<NsrBO> selectList(NsrSelectBO nsrSelectBO);

    NsrBO update(NsrUpdateBO nsrUpdateBO);

    NsrBO insert(NsrBO nsrBO);

    NsrBO delete(String id);
}
