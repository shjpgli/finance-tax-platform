package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendListBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 11:07
 */
public interface RealNameValidationService {
    List<UserExtendListBO> selectList(Map map);

    UserExtendBO validate(String userId, String validStatus, UserExtendUpdateBO userExtendUpdateBO) throws ParseException;

}
