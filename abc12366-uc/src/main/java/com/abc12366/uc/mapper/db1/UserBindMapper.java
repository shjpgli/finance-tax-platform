package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;

import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
public interface UserBindMapper {
    int dzsbBind(UserDzsb userDzsb);

    int dzsbUnbind(String id);

    int hngsBind(UserHngs userHngs);

    int hngsUnbind(String id);

    int hndsBind(UserHnds userHnds);

    int hndsUnbind(String id);

    int update(UserDzsb userDzsb);

    int updateBatch(Map<String,Object> map);

    int updateHngs(UserHngs userHngs);
}
