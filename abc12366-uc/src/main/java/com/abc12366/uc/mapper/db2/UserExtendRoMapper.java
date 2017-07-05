package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;

import java.util.List;

/**
 * Created by lgy on 2017-05-05.
 */
public interface UserExtendRoMapper {

    UserExtend selectOne(String userId);

    List<UserExtendBO> selectList(String username);
}
