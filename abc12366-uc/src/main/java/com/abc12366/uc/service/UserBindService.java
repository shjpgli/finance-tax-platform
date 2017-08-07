package com.abc12366.uc.service;

import com.abc12366.uc.model.bo.*;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindService {
    UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws MarshalException, ValidationException;

    boolean dzsbUnbind(String id);

    UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) throws Exception;

    boolean hngsUnbind(String id);

    UserHndsBO hndsBind(UserHndsInsertBO userHndsInsertBO, HttpServletRequest request);

    boolean hndsUnbind(String id);

    List<UserDzsbListBO> getUserDzsbBind(String userId);

    List<UserHngsListBO> getUserhngsBind(String userId);

    List<UserHndsBO> getUserhndsBind(String userId);
}
