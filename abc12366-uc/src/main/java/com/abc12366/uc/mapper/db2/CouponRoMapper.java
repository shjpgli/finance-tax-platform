package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;
import com.abc12366.uc.model.order.CouponUser;
import com.abc12366.uc.model.order.bo.CouponActivityListBO;
import com.abc12366.uc.model.order.bo.CouponListBO;
import com.abc12366.uc.model.order.bo.CouponUserListBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-13 3:07 PM
 * @since 1.0.0
 */
public interface CouponRoMapper {

    /**
     * 查询优惠劵列表
     *
     * @param bo 查询条件：couponMode,couponType,status
     * @return 优惠劵列表
     */
    List<CouponListBO> selectList(Coupon bo);

    /**
     * 查询优惠劵活动列表
     *
     * @param bo 查询条件：activityName,couponId,status
     * @return 优惠劵列表
     */
    List<CouponActivityListBO> selectActivityList(CouponActivity bo);

    /**
     * 查询用户优惠劵活动列表
     *
     * @param bo userId,activityId,couponId,status,validEndTime,orderNo
     * @return 用户优惠劵活动列表
     */
    List<CouponUserListBO> selectUserList(CouponUser bo);

    /**
     * 查询用户优惠劵活动数量
     *
     * @param bo userId,activityId,status
     * @return 记录数量
     */
    int selectUserCouponCount(CouponUser bo);

    /**
     * 根据ID查看优惠劵
     *
     * @param id 优惠劵ID
     * @return 优惠劵对象
     */
    Coupon selectOne(String id);

    /**
     * 根据ID查看优惠劵活动
     *
     * @param id 活动ID
     * @return 优惠劵活动对象
     */
    CouponActivity selectOneActivity(String id);

    /**
     * 根据ID查询用户优惠劵对象
     *
     * @param id 用户优惠劵主键ID
     * @return 用户优惠劵对象
     */
    CouponUser selectUserCoupon(String id);

    /**
     * 根据优惠劵Ids查询用户优惠劵列表
     *
     * @param ids 优惠劵IDs
     * @return 用户优惠劵列表
     */
    List<CouponUser> selectUserCouponByIds(String ids);
}
