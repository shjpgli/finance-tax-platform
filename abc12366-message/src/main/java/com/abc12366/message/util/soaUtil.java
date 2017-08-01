package com.abc12366.message.util;

import org.springframework.http.ResponseEntity;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-28
 * Time: 17:05
 */
public class soaUtil {
    public static boolean isExchangeSuccessful(ResponseEntity response) {
        if (response != null && response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return true;
        }
        return false;
    }
}
