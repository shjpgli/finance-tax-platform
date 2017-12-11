package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.RigionStatisBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-07
 * Time: 16:11
 */
public interface UserStatisRoMapper {
    List<User> tagCountUsers(@Param("start")Date start, @Param("end")Date end, @Param("tagName")String tagName);

    List<RigionStatisBO> regionCountry(@Param("start")Date start, @Param("end")Date end);

    List<RigionStatisBO> regionProvince(@Param("start")Date start, @Param("end")Date end, @Param("province")String province);
}
