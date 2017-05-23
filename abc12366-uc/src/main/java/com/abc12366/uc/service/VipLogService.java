package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.VipLogBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:37
 */
public interface VipLogService {
    List<VipLogBO> selectList();

    VipLogBO insert(VipLogBO vipLogBO);
}
