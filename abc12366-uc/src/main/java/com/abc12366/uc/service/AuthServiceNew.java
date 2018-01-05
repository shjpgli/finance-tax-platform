package com.abc12366.uc.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RegisterBO;
import com.abc12366.uc.model.bo.UserReturnBO;

public interface AuthServiceNew {

	UserReturnBO register(RegisterBO registerBO, HttpServletRequest request);

	@SuppressWarnings("rawtypes")
	Map login(LoginBO loginBO, String string);

}
