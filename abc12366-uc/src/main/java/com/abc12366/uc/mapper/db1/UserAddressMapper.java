package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.UserAddress;
import org.apache.ibatis.annotations.Param;

/**
 *
 * UserAddressMapper���ݿ�����ӿ���
 *
 **/

public interface UserAddressMapper{


    /**
     *
     * ɾ������������IDɾ����
     *
     **/
    int deleteByPrimaryKey ( @Param("id") String id );

    /**
     *
     * ���
     *
     **/
    int insert( UserAddress record );

    /**

    /**
     *
     * �޸� ��ƥ����ֵ���ֶΣ�
     *
     **/
    int update( UserAddress record );

    int deleteByIdAndUserId(UserAddress userAddress);
}