package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserExtendBO;

import java.text.ParseException;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 11:07
 */
public interface RealNameValidationService {
    List<UserExtendBO> selectList(String username);

    UserExtendBO validate(String userId) throws ParseException;

    UserExtendBO reValidate(String userId) throws ParseException;
}
