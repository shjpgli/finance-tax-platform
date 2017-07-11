package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.bo.CollectBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-10
 * Time: 17:40
 */
public interface CollectService {
    CollectBO insert(String askId, HttpServletRequest request);

    void delete(String askId, HttpServletRequest request);

    List<CollectBO> selectList(String userId);

    String selectCount(String askId);
}
