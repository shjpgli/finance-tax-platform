package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Express;
import com.abc12366.uc.model.bo.ExpressBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ExpressMapper数据库操作接口类
 **/

public interface ExpressRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    Express selectByPrimaryKey(@Param("id") String id);


    List<ExpressBO> selectList(ExpressBO expressBO);

    List<Express> selectbyUserOrderNo(String userOrderNo);
}