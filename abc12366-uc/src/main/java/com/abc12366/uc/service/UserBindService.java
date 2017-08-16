package com.abc12366.uc.service;

import com.abc12366.uc.model.BaseObject;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.model.tdps.TY21Xml2Object;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindService {
    UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws Exception;

    boolean dzsbUnbind(String id);

    UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) throws Exception;

    boolean hngsUnbind(String id);

    UserHndsBO hndsBind(UserHndsInsertBO userHndsInsertBO, HttpServletRequest request);

    boolean hndsUnbind(String id);

    List<UserDzsbListBO> getUserDzsbBind(String userId);

    List<UserHngsListBO> getUserhngsBind(String userId);

    List<UserHndsBO> getUserhndsBind(String userId);

    TY21Xml2Object nsrLogin(NsrLogin login, HttpServletRequest request) throws Exception;

    BaseObject resetPassword(NsrResetPwd data, HttpServletRequest request) throws IOException, MarshalException, ValidationException;

    BaseObject updatePassword(UpdatePwd data);
}
