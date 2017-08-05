package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ContentCount;
import org.apache.ibatis.annotations.Param;

/**
 * ContentCountMapper数据库操作接口类
 **/

public interface ContentCountRoMapper {


    /**
     * 查询(根据主键ID查询)
     **/
    ContentCount selectByPrimaryKey(@Param("id") Long id);


}