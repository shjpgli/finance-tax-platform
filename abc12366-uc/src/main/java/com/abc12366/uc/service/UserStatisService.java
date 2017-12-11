package com.abc12366.uc.service;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.RigionStatisBO;
import com.abc12366.uc.model.bo.TagUserStaticBO;

import java.util.List;

/**
 * 数据统计服务接口类
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-07
 * Time: 10:54
 */
public interface UserStatisService {
    /**
     * 用户标签统计接口
     * @param type 时间类型 year，month，day
     * @param start 开始时间
     * @param end 结束时间
     * @param tagName 标签名
     * @return List<TagUserStaticBO>
     */
    List<TagUserStaticBO> tag(String type, String start, String end, String tagName);

    /**
     * 用户标签分析统计用户详情接口
     * @param type 时间类型 year，month，day
     * @param start 开始时间
     * @param end 结束时间
     * @param tagName 标签名
     * @return List<User>
     */
    List<User> userTagUinfo(String type, String start, String end, String tagName);

    /**
     * 用户区域统计接口
     * @param type 区域类型，country、province
     * @param start 开始时间
     * @param end 结束时间
     * @param province 省名
     * @return RigionStatisBO
     */
    List<RigionStatisBO> region(String type, String start, String end,String province);
}
