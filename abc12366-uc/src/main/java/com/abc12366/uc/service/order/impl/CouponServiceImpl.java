package com.abc12366.uc.service.order.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.CouponMapper;
import com.abc12366.uc.mapper.db2.CouponRoMapper;
import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;
import com.abc12366.uc.model.order.CouponUser;
import com.abc12366.uc.model.order.bo.*;
import com.abc12366.uc.service.order.CouponService;
import com.abc12366.uc.util.StringUtil;
import com.abc12366.uc.web.order.CartController;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 优惠劵服务实现类
 *
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-13 3:01 PM
 * @since 1.0.0
 */
@Service
public class CouponServiceImpl implements CouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    /**
     * 优惠类型-满减
     */
    public static final String COUPONTYPE_MANJIAN = "MANJIAN";

    /**
     * 优惠类型-折扣
     */
    public static final String COUPONTYPE_ZHEKOU = "ZHEKOU";

    /**
     * 优惠类型-立减
     */
    public static final String COUPONTYPE_LIJIAN = "LIJIAN";

    /**
     * 有效期类型-固定时间段
     */
    public static final String VALIDTYPE_PERIOD = "PERIOD";

    /**
     * 有效期类型-固定天数
     */
    public static final String VALIDTYPE_DAYS = "DAYS";

    /**
     * 优惠劵活动状态-启用
     */
    public static final String COUPON_ACTIVITY_STATUS_ON = "2";

    /**
     * 优惠劵CRUD操作
     */
    @Autowired
    private CouponMapper couponMapper;

    /**
     * 优惠劵查询操作
     */
    @Autowired
    private CouponRoMapper couponRoMapper;

    @Override
    public List<CouponListBO> selectList(Coupon bo, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return couponRoMapper.selectList(bo);
    }

    @Override
    public List<CouponActivityListBO> selectActivityList(CouponActivity bo, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<CouponActivityListBO> dataList = couponRoMapper.selectActivityList(bo);
        // todo 统计已产生优惠总额
        dataList.stream().filter(data -> COUPON_ACTIVITY_STATUS_ON.equals(data.getStatus())).forEach(data -> {
            CouponUser cu = new CouponUser();
            cu.setActivityId(bo.getId());
            cu.setCouponId(bo.getCouponId());
            couponRoMapper.selectUserList(cu);
        });
        return dataList;
    }

    @Override
    public List<CouponUserListBO> selectUserList(CouponUser bo, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return couponRoMapper.selectUserList(bo);
    }

    @Override
    public boolean insert(@Valid CouponBO bo) {
        checkInput(bo);
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(bo, coupon);
        coupon.setId(Utils.uuid());
        Date now = new Date();
        coupon.setCreateTime(now);
        coupon.setLastUpdate(now);
        return 1 == couponMapper.insert(coupon);
    }

    @Override
    public boolean update(@Valid CouponBO bo) {
        Assert.notNull(bo.getId(), "id can not empty");
        checkInput(bo);
        Coupon coupon = selectOne(bo.getId());
        coupon.setCouponName(bo.getCouponName());
        coupon.setCouponMode(bo.getCouponMode());
        coupon.setCouponType(bo.getCouponType());
        coupon.setParam1(bo.getParam1());

        coupon.setParam2(bo.getParam2());
        coupon.setAmountType(bo.getAmountType());
        coupon.setValidType(bo.getValidType());
        coupon.setValidStartTime(bo.getValidStartTime());
        coupon.setValidEndTime(bo.getValidEndTime());

        coupon.setValidDays(bo.getValidDays());
        coupon.setDescription(bo.getDescription());
        coupon.setStatus(bo.getStatus());
        coupon.setLastUpdate(new Date());
        return 1 == couponMapper.update(coupon);
    }

    @Override
    public Coupon selectOne(String id) {
        Assert.notNull(id, "id can not empty");
        return couponRoMapper.selectOne(id);
    }

    @Override
    public boolean delete(String id) {
        Assert.notNull(id, "id can not empty");
        Coupon coupon = selectOne(id);
        coupon.setStatus("0");
        coupon.setLastUpdate(new Date());
        return 1 == couponMapper.update(coupon);
    }

    @Override
    public boolean insertActivity(@Valid CouponActivityBO bo) {
        CouponActivity ca = new CouponActivity();
        ca.setId(Utils.uuid());
        ca.setActivityName(bo.getActivityName());
        ca.setActivityLink(bo.getActivityLink());
        ca.setCouponId(bo.getCouponId());
        ca.setActivityStartTime(bo.getActivityStartTime());

        ca.setActivityEndTime(bo.getActivityEndTime());
        ca.setCouponNum(bo.getCouponNum());
        ca.setLimit(bo.getLimit());
        ca.setLimitNum(bo.getLimitNum());
        ca.setGetType(bo.getGetType());

        ca.setValid(bo.getValid());
        ca.setValidApi(bo.getValidApi());
        ca.setTarget(bo.getTarget());
        ca.setAreaOper(bo.getAreaOper());
        ca.setAreaIds(StringUtil.list2Str(bo.getAreaIds()));

        ca.setTagOper(bo.getTagOper());
        ca.setTagIds(StringUtil.list2Str(bo.getTagIds()));
        ca.setRegTimeOper(bo.getRegTimeOper());
        ca.setRegStartTime(bo.getRegStartTime());
        ca.setRegEndTime(bo.getRegEndTime());

        ca.setVips(StringUtil.list2Str(bo.getVips()));
        ca.setUserIds(StringUtil.list2Str(bo.getUserIds()));
        ca.setDescription(bo.getDescription());
        ca.setImageUrl(bo.getImageUrl());
        ca.setStatus(bo.getStatus());

        Date now = new Date();
        ca.setCreateTime(now);
        ca.setLastUpdate(now);
        return 1 == couponMapper.insertActivity(ca);
    }

    @Override
    public boolean updateActivity(@Valid CouponActivityBO bo) {
        Assert.notNull(bo.getId(), "id can not empty");

        CouponActivity ca = selectOneActivity(bo.getId());
        ca.setActivityName(bo.getActivityName());
        ca.setActivityLink(bo.getActivityLink());
        ca.setCouponId(bo.getCouponId());
        ca.setActivityStartTime(bo.getActivityStartTime());

        ca.setActivityEndTime(bo.getActivityEndTime());
        ca.setCouponNum(bo.getCouponNum());
        ca.setLimit(bo.getLimit());
        ca.setLimitNum(bo.getLimitNum());
        ca.setGetType(bo.getGetType());

        ca.setValid(bo.getValid());
        ca.setValidApi(bo.getValidApi());
        ca.setTarget(bo.getTarget());
        ca.setAreaOper(bo.getAreaOper());
        ca.setAreaIds(StringUtil.list2Str(bo.getAreaIds()));

        ca.setTagOper(bo.getTagOper());
        ca.setTagIds(StringUtil.list2Str(bo.getTagIds()));
        ca.setRegTimeOper(bo.getRegTimeOper());
        ca.setRegStartTime(bo.getRegStartTime());
        ca.setRegEndTime(bo.getRegEndTime());

        ca.setVips(StringUtil.list2Str(bo.getVips()));
        ca.setUserIds(StringUtil.list2Str(bo.getUserIds()));
        ca.setDescription(bo.getDescription());
        ca.setImageUrl(bo.getImageUrl());
        ca.setStatus(bo.getStatus());

        ca.setLastUpdate(new Date());
        return 1 == couponMapper.updateActivity(ca);
    }

    @Override
    public CouponActivity selectOneActivity(String id) {
        Assert.notNull(id, "id can not empty");
        return couponRoMapper.selectOneActivity(id);
    }

    @Override
    public boolean deleteActivity(String id) {
        Assert.notNull(id, "id can not empty");
        CouponActivity ca = selectOneActivity(id);
        ca.setStatus("0");
        ca.setLastUpdate(new Date());
        return 1 == couponMapper.updateActivity(ca);
    }

    @Override
    public boolean userCollectCoupon(String userId, String activityId) {
        Assert.notNull(userId, "userId can not empty");
        Assert.notNull(activityId, "activityId can not empty");
        CouponActivity ca = selectOneActivity(activityId);
        if (ca != null) {
            Coupon c = selectOne(ca.getCouponId());
            if (c != null) {
                CouponUser cu = new CouponUser();
                Date now = new Date();
                cu.setId(Utils.uuid());
                cu.setUserId(userId);
                cu.setActivityId(activityId);
                cu.setCouponId(ca.getCouponId());
                cu.setCouponName(c.getCouponName());

                cu.setCouponMode(c.getCouponMode());
                cu.setCouponType(c.getCouponType());
                cu.setParam1(c.getParam1());
                cu.setParam2(c.getParam2());
                cu.setAmountType(c.getAmountType());

                cu.setValidStartTime(null);
                cu.setValidEndTime(null);
                cu.setDescription(c.getDescription());
                cu.setStatus("1");
                cu.setCreateTime(now);

                cu.setLastUpdate(now);
                return 1 == couponMapper.insertUserCoupon(cu);
            }
        }
        return false;
    }

    /**
     * 校验用户输入
     *
     * @param couponBO 优惠劵对象
     */
    private void checkInput(@Valid @RequestBody CouponBO couponBO) {
        // 优惠劵类型合法
        boolean couponType = StringUtils.isNotEmpty(couponBO.getCouponType()) &&
                (COUPONTYPE_MANJIAN.equalsIgnoreCase(couponBO.getCouponType()) ||
                        COUPONTYPE_ZHEKOU.equalsIgnoreCase(couponBO.getCouponType()) ||
                        COUPONTYPE_LIJIAN.equalsIgnoreCase(couponBO.getCouponType()));
        // 校验参数不为空
        if (couponType) {
            if (COUPONTYPE_MANJIAN.equalsIgnoreCase(couponBO.getCouponType()) ||
                    COUPONTYPE_ZHEKOU.equalsIgnoreCase(couponBO.getCouponType())) {
                if (couponBO.getParam1() == null || couponBO.getParam2() == null) {
                    throw new ServiceException(7100);
                }
            }
            if (COUPONTYPE_LIJIAN.equalsIgnoreCase(couponBO.getCouponType())) {
                if (couponBO.getParam2() == null) {
                    throw new ServiceException(7101);
                }
            }
        } else {
            throw new ServiceException(7102);
        }

        // 有效期类型合法
        boolean validType = StringUtils.isNotEmpty(couponBO.getValidType()) &&
                (VALIDTYPE_PERIOD.equalsIgnoreCase(couponBO.getValidType())
                        || VALIDTYPE_DAYS.equalsIgnoreCase(couponBO.getValidType()));
        if (validType) {
            // 校验有效期起止
            if (VALIDTYPE_PERIOD.equalsIgnoreCase(couponBO.getValidType())) {
                if (couponBO.getValidStartTime() == null || couponBO.getValidEndTime() == null) {
                    throw new ServiceException(7104);
                }
            }
            // 校验有效期天数
            if (VALIDTYPE_DAYS.equalsIgnoreCase(couponBO.getValidType())) {
                if (couponBO.getValidDays() == null) {
                    throw new ServiceException(7105);
                }
            }
        } else {
            throw new ServiceException(7103);
        }
    }
}
