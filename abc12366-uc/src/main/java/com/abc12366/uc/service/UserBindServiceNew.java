package com.abc12366.uc.service;

import javax.servlet.http.HttpServletRequest;

import com.abc12366.uc.model.bo.NsrLogin;
import com.abc12366.uc.model.bo.UserDzsbBO;
import com.abc12366.uc.model.bo.UserDzsbInsertBO;
import com.abc12366.uc.model.bo.UserHngsBO;
import com.abc12366.uc.model.bo.UserHngsInsertBO;
import com.abc12366.uc.model.tdps.TY21Xml2Object;
import com.abc12366.uc.wsbssoa.response.HngsNsrLoginResponse;

public interface UserBindServiceNew {

	UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws Exception;

	UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request);

	TY21Xml2Object nsrLogin(NsrLogin login, HttpServletRequest request) throws Exception;

	UserDzsbBO cszjdzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request)  throws Exception;

	UserHngsBO cszjhngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request);

	TY21Xml2Object cszjnsrLoginShb(NsrLogin login, HttpServletRequest request) throws Exception;

	HngsNsrLoginResponse nsrLoginDzsj(UserHngsInsertBO login, HttpServletRequest request);

}
