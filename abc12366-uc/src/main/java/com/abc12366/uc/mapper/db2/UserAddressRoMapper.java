package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserAddress;
import com.abc12366.uc.model.bo.UserAddressBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * UserAddressMapper数据库操作接口类
 *
 **/

public interface UserAddressRoMapper {


    /**
     *
     * 查询（根据主键ID查询）
     *
     **/
    UserAddress  selectByPrimaryKey ( @Param("id") String id );

    List<UserAddress> selectList(String userId);

    UserAddress selectOne(UserAddress userAddress);
}