package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.bo.CollectBO;

import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-11
 * Time: 10:10
 */
public interface CollectRoMapper {
    List<CollectBO> selectList(String userId);

    String selectCount(String askId);

    List<CollectBO> selectExist(Map map);
}
