package com.abc12366.cszj.service;

import com.abc12366.cszj.model.bo.AdPageBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 投票功能服务类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:19 PM
 * @since 1.0.0
 */
public interface AdPageService {
    List<AdPageBO> selectList(AdPageBO vote, int page, int size);

    AdPageBO insert(AdPageBO vote);

    AdPageBO selectOne(String id);

    AdPageBO update(AdPageBO vote);

    void delete(String id);
}
