package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserLoginPasswordWrongCount;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface UserMapper {

    int insert(User user);

    int update(User user);

    int delete(String id);

    int deleteContinuePwdWrong(String id);

    int insertContinuePwdWrong(UserLoginPasswordWrongCount wrongCount);

    int updateContinuePwdWrong(UserLoginPasswordWrongCount wrongCount);

    int updatePhone(User user);
}
