package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UCToken;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lgy on 2017-05-05.
 */
public interface UCTokenRoMapper {
    UCToken selectOne(@Param("userId") String userId, @Param("appId") String appId);
}
