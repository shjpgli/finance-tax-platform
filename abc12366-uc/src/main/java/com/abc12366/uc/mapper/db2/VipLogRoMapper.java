package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.VipLogBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:40
 */
public interface VipLogRoMapper {
    List<VipLogBO> selectList(String userId);
}
