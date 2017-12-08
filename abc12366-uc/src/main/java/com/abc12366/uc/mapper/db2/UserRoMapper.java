package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserLoginPasswordWrongCount;
import com.abc12366.uc.model.bo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-20 4:18 PM
 * @since 1.0.0
 */
public interface UserRoMapper {

    /**
     * 后台用户列表查询
     *
     * @param map 查询参数
     * @return 用户列表List
     */
    List<UserListBO> selectList(Map map);

    User selectOne(String id);

    User selectByUsernameOrPhone(LoginBO loginBO);

    UserBO selectOneByToken(String userToken);

    List<UserBO> selectListExcludedId(String id);

    User selectValidOne(String userId);

    UserBO selectByopenid(String openid);

    /**
     * 查询过期的会员列表
     *
     * @param date 目前定义为当前日期
     * @return List<User> 过期的用户列表
     */
    List<User> selectUserVipList(@Param("date") Date date);

    List<UserLoginPasswordWrongCount> selectContinuePwdWrong(String id);

    UserSimpleInfoBO selectSimple(String userId);

    int getAllNomalCont();

    List<UserBO> getNomalList(Map<String, Object> map);

    User selectByWxUserId(User users);

    List<User> findByHngsNsrsbh(String nsrsbh);

    UserBO selectOneByPhone(String phone);

    /**
     * 根据用户ID查询用户基本表信息
     *
     * @param user 用户条件
     * @return 用户基本表信息
     */
    User selectUserById(User user);

    /**
     * 统计用户，统计维度为【创建时间】
     * @param map
     * @return
     */
    List<UserStatisBO> statisUserByDay(Map<String, Object> map);


    /**
     * 用户登陆数
     * @param map
     * @return
     */
    UserLossRateBO statisUserLossRateCount(Map<String, Object> map);

    /**
     * 用户总数
     * @param map
     * @return
     */
    UserLossRateBO statisUserCount(Map<String, Object> map);

    /**
     * 用户活跃度统计(概况)接口
     * @return UserLivenessSurveyBO
     */
    UserLivenessSurveyBO userLivenessSurvey();
    /**
     * 用户活跃度统计(明细)接口
     * @return UserLivenessSurveyBO
     */
    UserLivenessDetailBO userLivenessDetail(@Param("start")Date start, @Param("end")Date end);

    Float selectExpCount(Map<String, Object> map);

    /**
     * 查询RFM指标
     * @param map
     * @return
     */
    UserRFMBO statisUserRFM(Map<String, Object> map);

    /**
     * 查询导出用户
     * @param map
     * @return
     */
    List<UserExprotInfoBO> statisUserConsumeLevel(Map<String, Object> map);

    /**
     * 用户活跃度统计详情中包含的用户信息接口
     * @param map
     * @return
     */
    List<UserListBO> userLivenessDetailUinfo(Map<String, Date> map);

    /**
     * 用户年龄分布统计
     * @param map
     * @return
     */
    Integer statisUserAge(Map<String, Object> map);

    /**
     * 查询用户总数
     * @return
     */
    Integer selectUserCount();

    /**
     * 用户性别分布统计
     * @param map
     * @return
     */
    List<UserSexBO> statisUserSex(Map<String, Object> map);


    /**
     * 用户服务企业情况统计
     * @param map
     * @return
     */
    UserBindBO statisUserBind(Map<String, Object> map);

    /**
     * 用户服务企业情况统计-用户列表
     * @param map
     * @return
     */
    List<UserBO> statisUserBindList(Map<String, Object> map);

    /**
     * 用户年龄分布统计-用户列表
     * @param map
     * @return
     */
    List<UserBO> statisUserAgeList(Map<String, Object> map);

    /**
     * 未知年龄-用户列表
     * @param map
     * @return
     */
    List<UserBO> statisUserUnknownAgeList(Map<String, Object> map);

    /**
     * 未知年龄-用户统计
     * @param map
     * @return
     */
    Integer statisUserUnknownAge(Map<String, Object> map);

    /**
     * 用户性别分布统计-用户列表
     * @param map
     * @return
     */
    List<UserBO> statisUserSexList(Map<String, Object> map);
}
