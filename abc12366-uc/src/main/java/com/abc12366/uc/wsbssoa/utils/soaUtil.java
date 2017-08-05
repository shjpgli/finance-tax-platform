package com.abc12366.uc.wsbssoa.utils;

import org.springframework.http.ResponseEntity;

/**
 * Admin: liuguiyao<435720953@qq.com>
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
