package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;

/**
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-13 3:06 PM
 * @since 1.0.0
 */
public interface CouponMapper {

    /**
     * 新增优惠劵
     *
     * @param coupon 优惠劵对象
     * @return 影响行数
     */
    int insert(Coupon coupon);

    /**
     * 修改优惠劵
     *
     * @param coupon 优惠劵对象
     * @return 影响行数
     */
    int update(Coupon coupon);

    int updateActivity(CouponActivity ca);
}
