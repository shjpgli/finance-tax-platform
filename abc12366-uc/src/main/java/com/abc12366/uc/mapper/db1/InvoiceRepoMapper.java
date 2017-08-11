package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.invoice.InvoiceRepo;
import org.apache.ibatis.annotations.Param;

/**
 * InvoiceRepoMapper数据库操作接口类
 **/

public interface InvoiceRepoMapper {


    /**
     * 删除（根据主键ID删除）
     **/
    int deleteByPrimaryKey(@Param("id") String id);

    /**
     * 添加
     **/
    int insert(InvoiceRepo record);

    /**
     * 修改 （匹配有值的字段）
     **/
    int update(InvoiceRepo record);

    int delete(String id);

}