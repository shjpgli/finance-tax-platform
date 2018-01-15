package com.abc12366.uc.service.order;

import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;
import com.abc12366.uc.model.order.CouponUser;
import com.abc12366.uc.model.order.bo.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 优惠劵服务
 *
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-13 11:57 AM
 * @since 1.0.0
 */
public interface CouponService {

    /**
     * 查询优惠劵列表
     *
     * @param bo       优惠劵查询对象
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 优惠劵列表
     */
    List<CouponListBO> selectList(Coupon bo, int pageNum, int pageSize);

    /**
     * 查询优惠劵活动列表
     *
     * @param bo       查询活动对象
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 优惠劵列表
     */
    List<CouponActivityListBO> selectActivityList(CouponActivity bo, int pageNum, int pageSize);

    /**
     * 查询优惠劵活动用户列表
     *
     * @param bo       优惠劵活动用户查询条件
     * @param pageNum  当前页
     * @param pageSize 没有大小
     * @return 优惠劵活动用户列表
     */
    List<CouponUserListBO> selectUserList(CouponUser bo, int pageNum, int pageSize);

    /**
     * 新增优惠劵
     *
     * @param couponBO 优惠劵对象
     * @return 成功或失败
     */
    boolean insert(@Valid CouponBO couponBO);

    /**
     * 修改优惠劵
     *
     * @param couponBO 优惠劵对象
     * @return 成功或失败
     */
    boolean update(@Valid CouponBO couponBO);

    /**
     * 查看优惠劵
     *
     * @param id 优惠劵ID
     * @return 优惠劵对象
     */
    Coupon selectOne(String id);

    /**
     * 逻辑删除优惠劵
     *
     * @param id 优惠劵ID
     * @return 成功或失败
     */
    boolean delete(String id);

    /**
     * 新增优惠劵活动
     * @param bo 优惠劵活动BO
     * @return 是否成功
     */
    boolean insertActivity(@Valid CouponActivityBO bo);

    /**
     * 修改优惠劵活动
     * @param bo 优惠劵活动BO
     * @return 是否成功
     */
    boolean updateActivity(@Valid CouponActivityBO bo);

    /**
     * 查看优惠劵活动
     * @param id 活动ID
     * @return 优惠劵活动对象
     */
    CouponActivity selectOneActivity(String id);

    /**
     * 逻辑删除优惠劵
     * @param id 优惠劵活动ID
     * @return 是否成功
     */
    boolean deleteActivity(String id);
}
