package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.BindCountInfo;
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

    List<RigionStatisBO> regionCountry(@Param("start")Date start, @Param("end")Date end, @Param("tagName")List<String> tagName);

    List<RigionStatisBO> regionProvince(@Param("start")Date start, @Param("end")Date end, @Param("province")String province, @Param("tagName")List<String> tagName);

    List<User> regionProvinceUinfo(@Param("start")Date start, @Param("end")Date end, @Param("province") String province,@Param("tagName")List<String> tagName);

    List<User> regionCityUinfo(@Param("start")Date start, @Param("end")Date end, @Param("city")String city,@Param("tagName")List<String> tagName);

    int bindDzsb(@Param("start")Date start, @Param("end")Date end);

    int bindHngs(@Param("start")Date start, @Param("end")Date end);

    int bindHnds(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindDzsbInfo(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindHngsInfo(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindHndsInfo(@Param("start")Date start, @Param("end")Date end);

    int bindDzsbLogin(@Param("start")Date start, @Param("end")Date end);

    int bindHngsLogin(@Param("start")Date start, @Param("end")Date end);

    int bindHndsLogin(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindDzsbLoginInfo(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindHngsLoginInfo(@Param("start")Date start, @Param("end")Date end);

    List<BindCountInfo> bindHndsLoginInfo(@Param("start")Date start, @Param("end")Date end);

//    void compreFactorsCountry(@Param("start")Date start, @Param("end")Date end,@Param("tagName")String tagName);
//
//    void compreFactorsProvince(@Param("start")Date start, @Param("end")Date end,@Param("tagName")String tagName, @Param("province")String province);
}
