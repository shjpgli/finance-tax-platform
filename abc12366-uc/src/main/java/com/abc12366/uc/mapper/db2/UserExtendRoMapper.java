package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendListBO;

import java.util.List;
import java.util.Map;

/**
 * Created by lgy on 2017-05-05.
 */
public interface UserExtendRoMapper {

    UserExtend selectOne(String userId);

    List<UserExtendListBO> selectList(Map map);

    UserExtend isRealName(String userId);

    UserExtend selectOneForAdmin(String userId);

    /**
     * 查询认证状态为【待认证】数量
     * @return Integer 数量
     */
    Integer selectTodoListCount();

	List<Map<String, String>> canmerging(Map map);
}
