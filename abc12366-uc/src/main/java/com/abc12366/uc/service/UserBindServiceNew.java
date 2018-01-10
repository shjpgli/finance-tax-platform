package com.abc12366.uc.service;

import javax.servlet.http.HttpServletRequest;

import com.abc12366.uc.model.bo.UserDzsbBO;
import com.abc12366.uc.model.bo.UserDzsbInsertBO;
import com.abc12366.uc.model.bo.UserHngsBO;
import com.abc12366.uc.model.bo.UserHngsInsertBO;

public interface UserBindServiceNew {

	UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws Exception;

	UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request);

}
