package com.abc12366.gateway.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-26
 * Time: 14:32
 */
public interface UpointService {
    void compute(HttpServletRequest request) throws IOException;
}
