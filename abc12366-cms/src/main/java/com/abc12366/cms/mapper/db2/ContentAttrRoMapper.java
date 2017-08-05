package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.ContentAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ContentAttrMapper数据库操作接口类
 **/

public interface ContentAttrRoMapper {


    /**
     * 查询(根据主键ID查询)
     **/
    ContentAttr selectByPrimaryKey(@Param("id") Long id);

    /**
     * 查询(根据contentId查询)
     **/
    List<ContentAttr> selectContentAttrList(String contentId);

}