package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserAddress;
import com.abc12366.uc.model.bo.UserAddressBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserAddressRoMapper {


    UserAddress  selectByPrimaryKey ( @Param("id") String id );

    List<UserAddress> selectList(String userId);

    UserAddress selectOne(UserAddress userAddress);

    List<UserAddress> selectByUserId(UserAddress userAddress);
}