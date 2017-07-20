package com.abc12366.gateway.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-21
 * Time: 9:49
 */
public interface UcUserService {

    boolean isAuthentication(String adminToken, String userToken, HttpServletRequest request);
}
