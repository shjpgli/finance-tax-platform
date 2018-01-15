package com.abc12366.uc.service.order.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.DateUtils;
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

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 优惠劵活动状态-已领取
     */
    public static final String COUPON_STATUS_GET = "1";

    /**
     * 优惠劵活动状态-启用
     */
    public static final String COUPON_STATUS_ON = "2";

    /**
     * 优惠劵活动状态-已冻结
     */
    public static final String COUPON_STATUS_FREEZEN = "3";

    /**
     * 优惠劵活动状态-已删除
     */
    public static final String COUPON_STATUS_DEL = "4";

    /**
     * 领取方式-系统
     */
    public static final String COUPON_GETTYPE_SYSTEM = "SYSTEM";

    /**
     * 领取方式-用户
     */
    public static final String COUPON_GETTYPE_USER = "USER";

    /**
     * 发放目标-全部用户
     */
    public static final String TARGET_1 = "1";

    /**
     * 发放目标-部分用户
     */
    public static final String TARGET_2 = "2";

    /**
     * 发放目标-特定用户
     */
    public static final String TARGET_3 = "3";

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
        // todo 统计数量
        dataList.stream().filter(data -> COUPON_STATUS_ON.equals(data.getStatus())).forEach(data -> {
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
        Date now = new Date();

        BeanUtils.copyProperties(bo, coupon);
        coupon.setId(Utils.uuid());
        coupon.setCategoryIds(StringUtil.list2Str(bo.getCategoryIds()));
        coupon.setCreateTime(now);
        coupon.setLastUpdate(now);
        return 1 == couponMapper.insert(coupon);
    }

    @Override
    public boolean update(@Valid CouponBO bo) {
        Assert.notNull(bo.getId(), "id can not empty");
        checkInput(bo);
        // 当优惠劵在活动中被使用时，不允许修改优惠劵
        isAllowUpdateCoupon(bo.getId());

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
        coupon.setCategoryIds(StringUtil.list2Str(bo.getCategoryIds()));
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
        // 当优惠劵在活动中被使用时，不允许操作优惠劵
        isAllowUpdateCoupon(id);

        Coupon coupon = selectOne(id);
        coupon.setStatus("0");
        coupon.setLastUpdate(new Date());
        return 1 == couponMapper.update(coupon);
    }

    @Override
    public boolean insertActivity(@Valid CouponActivityBO bo) {
        checkInput(bo);

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
        checkInput(bo);

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
            // 是否可以领取
            isAllowCollect(userId, ca);
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

                if (VALIDTYPE_PERIOD.equalsIgnoreCase(c.getValidType())) {
                    cu.setValidStartTime(c.getValidStartTime());
                    cu.setValidEndTime(c.getValidEndTime());
                } else {
                    cu.setValidEndTime(now);
                    cu.setValidEndTime(DateUtils.addDays(now, c.getValidDays()));
                }
                cu.setDescription(c.getDescription());
                cu.setStatus("1");
                cu.setCreateTime(now);

                cu.setLastUpdate(now);
                cu.setCategoryIds(c.getCategoryIds());
                return 1 == couponMapper.insertUserCoupon(cu);
            }
        }
        return false;
    }

    @Override
    public boolean userUseCoupon(Map<String, String> map) {
        Assert.notNull(map, "map can not empty");
        CouponUser cu = couponRoMapper.selectUserCoupon(map.get("couponId"));
        if (cu != null) {
            if (!cu.getUserId().equals(map.get("userId"))) {
                throw new ServiceException(7118);
            }
            if (!cu.getCategoryIds().contains(map.get("categoryId"))) {
                throw new ServiceException(7119);
            }
            Date now = new Date();
            if (now.before(cu.getValidStartTime()) || now.after(cu.getValidEndTime())) {
                throw new ServiceException(7120);
            }
            if (!COUPON_STATUS_ON.equals(cu.getStatus()) || !COUPON_STATUS_FREEZEN.equals(cu.getStatus())) {
                throw new ServiceException(7121);
            }
            cu.setOrderNo(map.get("orderNo"));
            cu.setStatus(map.get("status"));
            cu.setLastUpdate(new Date());
            return 1 == couponMapper.updateUserCoupon(cu);
        }
        return false;
    }

    @Override
    public boolean userDeleteCoupon(CouponUser bo) {
        Assert.notNull(bo.getId(), "map can not empty");
        CouponUser cu = couponRoMapper.selectUserCoupon(bo.getId());
        if (cu != null) {
            if (!cu.getUserId().equals(bo.getUserId())) {
                throw new ServiceException(7118);
            }
            if (!COUPON_STATUS_GET.equals(cu.getStatus()) || !COUPON_STATUS_FREEZEN.equals(cu.getStatus())) {
                throw new ServiceException(7121);
            }
            cu.setStatus(COUPON_STATUS_DEL);
            cu.setLastUpdate(new Date());
            return 1 == couponMapper.updateUserCoupon(cu);
        }
        return false;
    }

    /**
     * 是否允许用户领取
     *
     * @param userId 用户ID
     * @param ca     优惠劵活动对象
     */
    private void isAllowCollect(String userId, CouponActivity ca) {
        LOGGER.info("活动状态判断:{}", ca.getId());
        if (!COUPON_STATUS_ON.equals(ca.getStatus())) {
            throw new ServiceException(7108);
        }
        LOGGER.info("活动有效期判断");
        Date now = new Date();
        if (now.before(ca.getActivityStartTime()) || now.after(ca.getActivityEndTime())) {
            throw new ServiceException(7109);
        }

        CouponUser cu = new CouponUser();
        cu.setActivityId(ca.getId());
        int i = couponRoMapper.selectUserCouponCount(cu);
        LOGGER.info("活动优惠劵数量限制:{}", i);
        if (i > ca.getCouponNum()) {
            throw new ServiceException(7110);
        }

        if (ca.getLimit()) {
            cu.setUserId(userId);
            i = couponRoMapper.selectUserCouponCount(cu);
            LOGGER.info("用户领取限制:{}", i);
            if (i > ca.getLimitNum()) {
                throw new ServiceException(7111);
            }
        }
    }

    /**
     * 校验用户输入
     *
     * @param bo 优惠劵对象
     */
    private void checkInput(CouponBO bo) {
        // 优惠劵类型合法
        boolean couponType = StringUtils.isNotEmpty(bo.getCouponType()) &&
                (COUPONTYPE_MANJIAN.equalsIgnoreCase(bo.getCouponType()) ||
                        COUPONTYPE_ZHEKOU.equalsIgnoreCase(bo.getCouponType()) ||
                        COUPONTYPE_LIJIAN.equalsIgnoreCase(bo.getCouponType()));
        // 校验参数不为空
        if (couponType) {
            if (COUPONTYPE_MANJIAN.equalsIgnoreCase(bo.getCouponType()) ||
                    COUPONTYPE_ZHEKOU.equalsIgnoreCase(bo.getCouponType())) {
                if (bo.getParam1() == null || bo.getParam2() == null) {
                    throw new ServiceException(7100);
                }
            }
            if (COUPONTYPE_LIJIAN.equalsIgnoreCase(bo.getCouponType())) {
                if (bo.getParam2() == null) {
                    throw new ServiceException(7101);
                }
            }
        } else {
            throw new ServiceException(7102);
        }

        // 有效期类型合法
        boolean validType = StringUtils.isNotEmpty(bo.getValidType()) &&
                (VALIDTYPE_PERIOD.equalsIgnoreCase(bo.getValidType())
                        || VALIDTYPE_DAYS.equalsIgnoreCase(bo.getValidType()));
        if (validType) {
            // 校验有效期起止
            if (VALIDTYPE_PERIOD.equalsIgnoreCase(bo.getValidType())) {
                if (bo.getValidStartTime() == null || bo.getValidEndTime() == null) {
                    throw new ServiceException(7104);
                }
            }
            // 校验有效期天数
            if (VALIDTYPE_DAYS.equalsIgnoreCase(bo.getValidType())) {
                if (bo.getValidDays() == null) {
                    throw new ServiceException(7105);
                }
            }
        } else {
            throw new ServiceException(7103);
        }
    }

    /**
     * 校验用户输入
     *
     * @param bo 优惠劵活动对象
     */
    private void checkInput(CouponActivityBO bo) {
        // 领取限制
        if (bo.getLimit()) {
            if (bo.getLimitNum() == null) {
                throw new ServiceException(7112);
            }
        }
        // 领取方式
        if (!COUPON_GETTYPE_SYSTEM.equalsIgnoreCase(bo.getGetType()) ||
                !COUPON_GETTYPE_USER.equalsIgnoreCase(bo.getGetType())) {
            throw new ServiceException(7113);
        }
        // 外部接口
        if (bo.getValid() && StringUtils.isEmpty(bo.getValidApi())) {
            throw new ServiceException(7114);
        }
        // 发放目标
        if (TARGET_1.equals(bo.getTarget()) || TARGET_2.equals(bo.getTarget()) || TARGET_3.equals(bo.getTarget())) {
            if (TARGET_2.equals(bo.getTarget())) {
                if (bo.getAreaIds() == null && bo.getTagIds() == null &&
                        bo.getRegStartTime().after(bo.getRegEndTime()) &&
                        bo.getVips() == null) {
                    throw new ServiceException(7116);
                }
            }
            if (TARGET_3.equals(bo.getTarget())) {
                if (bo.getUserIds() == null || bo.getUserIds().size() < 0) {
                    throw new ServiceException(7115);
                }
            }
        }
        // 如果活动启用，请确保优惠劵已启用
        Coupon c = selectOne(bo.getId());
        if (COUPON_STATUS_ON.equals(bo.getStatus())) {
            if (c == null || !COUPON_STATUS_ON.equals(c.getStatus())) {
                throw new ServiceException(7107);
            }
        }

        // 活动有效期限制
        if (bo.getActivityStartTime().after(c.getValidStartTime()) ||
                bo.getActivityEndTime().before(c.getValidEndTime()) ||
                bo.getActivityEndTime().before(bo.getActivityEndTime()))  {
            throw new ServiceException(7117);
        }
    }

    /**
     * 当优惠劵在活动中被使用时，不允许修改优惠劵
     *
     * @param couponId 优惠劵ID
     */
    private void isAllowUpdateCoupon(String couponId) {
        CouponActivity ca = new CouponActivity();
        ca.setCouponId(couponId);
        ca.setStatus(COUPON_STATUS_ON);
        if (couponRoMapper.selectActivityList(ca).size() != 0) {
            throw new ServiceException(7106);
        }
    }
}
