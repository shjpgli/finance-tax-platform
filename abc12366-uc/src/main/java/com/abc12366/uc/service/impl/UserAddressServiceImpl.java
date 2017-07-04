package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.UserAddressMapper;
import com.abc12366.uc.mapper.db2.UserAddressRoMapper;
import com.abc12366.uc.model.UserAddress;
import com.abc12366.uc.model.bo.UserAddressBO;
import com.abc12366.uc.service.UserAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {


    @Autowired
    private UserAddressRoMapper userAddressRoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> selectList(String userId) {
        List<UserAddress> addressList = userAddressRoMapper.selectList(userId);
        return addressList;
    }

    @Override
    public UserAddressBO selectOne(UserAddress userAddress) {
        UserAddress address = new UserAddress();
        address = userAddressRoMapper.selectOne(userAddress);
        UserAddressBO bo = new UserAddressBO();
        BeanUtils.copyProperties(address,bo);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public UserAddressBO updateUserAddress(UserAddressBO userAddressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBO,userAddress);
        Date date = new Date();
        userAddress.setLastUpdate(date);
        int insert = userAddressMapper.update(userAddress);
        if(insert != 1){
            throw new ServiceException(4102);
        }
        UserAddressBO bo = new UserAddressBO();
        BeanUtils.copyProperties(userAddress,bo);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public int deleteByIdAndUserId(UserAddressBO userAddressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBO,userAddress);
        return userAddressMapper.deleteByIdAndUserId(userAddress);
    }

    @Transactional("db1TxManager")
    @Override
    public UserAddressBO addUserAddress(UserAddressBO userAddressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBO,userAddress);
        userAddress.setId(Utils.uuid());
        Date date = new Date();
        userAddress.setCreateTime(date);
        userAddress.setLastUpdate(date);
        int insert = userAddressMapper.insert(userAddress);
        if(insert != 1){
            throw new ServiceException(4101);
        }
        UserAddressBO bo = new UserAddressBO();
        BeanUtils.copyProperties(userAddress,bo);
        return bo;
    }
}
