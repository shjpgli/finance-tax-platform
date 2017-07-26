package com.abc12366.cszj.mapper.db2;

import com.abc12366.cszj.model.bo.AdPageBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface AdPageRoMapper {

    List<AdPageBO> selectList(AdPageBO adPageBO);

    AdPageBO selectOne(String id);
}
