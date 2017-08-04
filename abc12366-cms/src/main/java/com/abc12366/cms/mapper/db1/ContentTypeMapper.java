package com.abc12366.cms.mapper.db1;

import com.abc12366.cms.model.ContentType;
import org.apache.ibatis.annotations.Param;

/**
 * ContentTypeMapper数据库操作接口类
 **/

public interface ContentTypeMapper {

    /**
     * 删除(根据主键ID删除)
     **/
    int deleteByPrimaryKey(@Param("typeId") String typeId);

    /**
     * 添加
     **/
    int insert(ContentType record);

    /**
     * 添加(匹配有值的字段)
     **/
    int insertSelective(ContentType record);

    /**
     * 修改(匹配有值的字段)
     **/
    int updateByPrimaryKeySelective(ContentType record);

    /**
     * 修改(根据主键ID修改)
     **/
    int updateByPrimaryKey(ContentType record);

}