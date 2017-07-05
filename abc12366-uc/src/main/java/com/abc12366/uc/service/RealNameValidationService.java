package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserExtendBO;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 11:07
 */
public interface RealNameValidationService {
    List<UserExtendBO> selectList(String username);
}
