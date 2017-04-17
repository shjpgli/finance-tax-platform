package com.abc12366.gateway.service;

/**
 * 黑名单服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-06 1:46 PM
 * @since 1.0.0
 */
public interface BlacklistService {

    /**
     * 是否为黑名单
     *
     * @param addr IP地址
     * @return true: 是名单，false:不是黑名单
     */
    boolean isBlacklist(String addr);
}
