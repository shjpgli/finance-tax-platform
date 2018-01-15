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
     * @param bo 查询条件：couponMode、couponType、status
     * @return 优惠劵列表
     */
    List<CouponListBO> selectList(Coupon bo);

    /**
     * 查询优惠劵活动列表
     *
     * @param bo 查询条件：activityName、status
     * @return 优惠劵列表
     */
    List<CouponActivityListBO> selectActivityList(CouponActivity bo);

    /**
     * 查询用户优惠劵活动列表
     *
     * @param bo activityId, couponId, status
     * @return 用户优惠劵活动列表
     */
    List<CouponUserListBO> selectUserList(CouponUser bo);

    /**
     * 根据ID查看优惠劵
     *
     * @param id 优惠劵ID
     * @return 优惠劵对象
     */
    Coupon selectOne(String id);
}
