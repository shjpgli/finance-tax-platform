package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserLoginPasswordWrongCount;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserListBO;
import com.abc12366.uc.model.bo.UserSimpleInfoBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface UserRoMapper {

    /**
     * 后台用户列表查询
     *
     * @return 用户列表List
     * @map 查询参数
     */
    List<UserListBO> selectList(Map map);

    User selectOne(String id);

    User selectByUsernameOrPhone(LoginBO loginBO);

    UserBO selectOneByToken(String userToken);

    List<UserBO> selectListExcludedId(String id);

    User selectValidOne(String userId);

    UserBO selectByopenid(String openid);

    /**
     * 查询过期的会员列表
     *
     * @param date 目前定义为当前日期
     * @return List<User> 过期的用户列表
     */
    List<User> selectUserVipList(@Param("date") Date date);

    List<UserLoginPasswordWrongCount> selectContinuePwdWrong(String id);

    UserSimpleInfoBO selectSimple(String userId);

    int getAllNomalCont();

    List<UserBO> getNomalList(Map<String, Object> map);

    User selectByWxUserId(User users);

    List<User> findByHngsNsrsbh(String nsrsbh);

    UserBO selectOneByPhone(String phone);
}
