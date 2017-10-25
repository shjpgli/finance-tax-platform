package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserUpdateBO;
import com.abc12366.uc.model.UserLoginPasswordWrongCount;
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

    List<UserBO> selectList(Map map);

    User selectOne(String id);

    User selectByUsernameOrPhone(LoginBO loginBO);

    UserBO selectOneByToken(String userToken);

    List<UserBO> selectListExcludedId(String id);

    User selectValidOne(String userId);

	UserBO selectByopenid(String openid);

    List<User> selectUserVipList(@Param("date")Date date);

    List<UserLoginPasswordWrongCount> selectContinuePwdWrong(String id);

    UserSimpleInfoBO selectSimple(String userId);

	int getAllNomalCont();

	List<UserBO> getNomalList(Map<String, Object> map);

	User selectByWxUserId(UserUpdateBO userUpdateDTO);
}
