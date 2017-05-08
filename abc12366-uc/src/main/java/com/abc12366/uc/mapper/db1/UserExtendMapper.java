package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.UserExtend;

/**
 * Created by lgy on 2017-05-05.
 */
public interface UserExtendMapper {
    int insert(UserExtend userExtend);

    int delete(String userId);

    int update(UserExtend userExtend);
}
