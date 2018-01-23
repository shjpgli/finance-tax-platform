package com.abc12366.uc.web.order;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;
import com.abc12366.uc.model.order.CouponUser;
import com.abc12366.uc.model.order.bo.*;
import com.abc12366.uc.service.order.CouponService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠劵控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-13 11:25 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    /******************************优惠劵******************************/
    /**
     * 优惠劵服务
     */
    @Autowired
    private CouponService couponService;

    /**
     * 运营系统-查询优惠劵列表
     *
     * @param couponMode 优惠劵模式：固定-FIXED, 浮动-FLOAT
     * @param couponType 优惠类型：满减-MANJIAN，立减-LIJIAN，折扣-ZHEKOU
     * @param status     状态
     * @param pageNum    当前页
     * @param pageSize   每页大小
     * @return 优惠劵列表
     */
    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "couponMode", required = false) String couponMode,
                                     @RequestParam(value = "couponType", required = false) String couponType,
                                     @RequestParam(value = "status", required = false) String status,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {

        Coupon bo = new Coupon();
        if (StringUtils.isNotEmpty(couponMode)) {
            bo.setCouponMode(couponMode);
        }
        if (StringUtils.isNotEmpty(couponType)) {
            bo.setCouponType(couponType);
        }
        if (StringUtils.isNotEmpty(status)) {
            bo.setStatus(status);
        }
        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponListBO> dataList = couponService.selectList(bo, pageNum, pageSize);
        PageInfo<CouponListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 运营系统-新增优惠劵
     *
     * @param couponBO 优惠劵对象
     * @return 成功或失败
     */
    @PostMapping()
    public ResponseEntity insert(@Valid @RequestBody CouponBO couponBO) {
        LOGGER.info("{}", couponBO);
        return ResponseEntity.ok(Utils.kv("data", couponService.insert(couponBO)));
    }

    /**
     * 运营系统-修改优惠劵
     *
     * @param id       优惠劵ID
     * @param couponBO 优惠劵对象
     * @return 成功或失败
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @Valid @RequestBody CouponBO couponBO) {
        couponBO.setId(id);
        LOGGER.info("{}", couponBO);
        return ResponseEntity.ok(Utils.kv("data", couponService.update(couponBO)));
    }

    /**
     * 运营系统-查看优惠劵
     *
     * @param id 优惠劵ID
     * @return 优惠劵对象
     */
    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.selectOne(id)));
    }

    /**
     * 运营系统-逻辑删除优惠劵
     *
     * @param id 优惠劵ID
     * @return 成功或失败
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.delete(id)));
    }

    /******************************* 优惠劵活动 ******************************/

    /**
     * 运营系统-查询优惠劵活动列表
     *
     * @param activityName 活动名称
     * @param status       活动状态
     * @param pageNum      当前页
     * @param pageSize     每页大小
     * @return 优惠劵活动列表
     */
    @GetMapping("/activity")
    public ResponseEntity selectActivityList(
            @RequestParam(value = "activityName", required = false) String activityName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {

        CouponActivity bo = new CouponActivity();
        if (StringUtils.isNotEmpty(activityName)) {
            bo.setActivityName(activityName);
        }
        if (StringUtils.isNotEmpty(status)) {
            bo.setStatus(status);
        }
        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponActivityListBO> dataList = couponService.selectActivityList(bo, pageNum, pageSize);
        PageInfo<CouponActivityListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
    }

    /**
     * 前端-查询可用的优惠劵活动列表
     *
     * @param activityName 活动名称
     * @param pageNum      当前页
     * @param pageSize     每页大小
     * @return 可用的优惠劵活动列表
     */
    @GetMapping("/activities")
    public ResponseEntity selectUsableActivityList(
            @RequestParam(value = "activityName", required = false) String activityName,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        CouponActivity bo = new CouponActivity();
        if (StringUtils.isNotEmpty(activityName)) {
            bo.setActivityName(activityName);
        }
        bo.setStatus("2");
        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponActivityListBO> dataList = couponService.selectActivityList(bo, pageNum, pageSize);
        PageInfo<CouponActivityListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
    }

    /**
     * 运营系统-新增优惠劵活动
     *
     * @param bo 优惠劵活动BO
     * @return 是否成功新增
     */
    @PostMapping("/activity")
    public ResponseEntity insertActivity(@Valid @RequestBody CouponActivityBO bo) {
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.insertActivity(bo)));
    }

    /**
     * 运营系统-修改优惠劵活动
     *
     * @param id 活动ID
     * @param bo 优惠劵活动BO
     * @return 是否成功更新
     */
    @PutMapping("/activity/{id}")
    public ResponseEntity updateActivity(@PathVariable String id, @Valid @RequestBody CouponActivityBO bo) {
        bo.setId(id);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.updateActivity(bo)));
    }

    /**
     * 通用-查看优惠劵活动
     *
     * @param id 活动ID
     * @return 优惠劵活动对象
     */
    @GetMapping("/activity/{id}")
    public ResponseEntity selectOneActivity(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.selectOneActivity(id)));
    }

    /**
     * 运营系统-逻辑删除优惠劵活动
     *
     * @param id 活动ID
     * @return 是否成功删除
     */
    @DeleteMapping("/activity/{id}")
    public ResponseEntity deleteActivity(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.deleteActivity(id)));
    }

    /******************************* 用户优惠劵 ******************************/

    /**
     * 运营系统-查询优惠劵活动用户优惠劵列表
     *
     * @param orderNo  订单号
     * @param status   优惠劵状态
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 优惠劵活动用户优惠劵列表
     */
    @GetMapping("/user")
    public ResponseEntity selectUserList(
            @RequestParam(value = "orderNo", required = false) String orderNo,
            @RequestParam(value = "categoryIds", required = false) String categoryIds,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {

        CouponUser bo = new CouponUser();
        if (StringUtils.isNotEmpty(status)) {
            bo.setStatus(status);
        }
        if (StringUtils.isNotEmpty(orderNo)) {
            bo.setOrderNo(orderNo);
        }
        if (StringUtils.isNotEmpty(categoryIds)) {
            bo.setCategoryIds(categoryIds);
        }

        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponUserListBO> dataList = couponService.selectUserList(bo, pageNum, pageSize);
        PageInfo<CouponUserListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
    }

    /**
     * 前端-用户领取某一活动的优惠劵
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return 是否领取成功
     */
    @GetMapping("/user/{userId}/{activityId}")
    public ResponseEntity userCollectCoupon(@PathVariable String userId, @PathVariable String activityId,HttpServletRequest request) {
        CouponUser cu = new CouponUser();
        cu.setUserId(userId);
        cu.setActivityId(activityId);
        LOGGER.info("{}", cu);
        return ResponseEntity.ok(Utils.kv("data", couponService.userCollectCoupon(userId, activityId,request)));
    }

    /**
     * 前端-查询用户所有优惠劵列表
     *
     * @param userId   用户ID
     * @param status   优惠劵状态:0-未领取 1-已领取 2-已使用 3-已冻结 4-已删除 5-已过期 6-已作废
     * @param pageNum  当前页
     * @param pageSize 没有大小
     * @return 用户所有优惠劵列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity selectUserCouponList(
            @PathVariable String userId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {

        CouponUser bo = new CouponUser();
        if (StringUtils.isNotEmpty(status)) {
            bo.setStatus(status);
        }
        bo.setUserId(userId);
        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponUserListBO> dataList = couponService.selectUserList(bo, pageNum, pageSize);
        PageInfo<CouponUserListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
    }

    /**
     * 前端-用户逻辑删除优惠劵
     *
     * @param userId   用户ID
     * @param couponId 优惠劵ID
     * @return 成功或失败
     */
    @DeleteMapping("/user/{userId}/{couponId}")
    public ResponseEntity userDeleteCoupon(@PathVariable String userId, @PathVariable String couponId) {
        CouponUser bo = new CouponUser();
        bo.setUserId(userId);
        bo.setId(couponId);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.userDeleteCoupon(bo)));
    }

    /**
     * 前端-计算使用优惠劵之后的金额
     *
     * @param bo 计算对象
     * @return 计算后的金额
     */
    @PostMapping("/order")
    public ResponseEntity calculateOrderAmount(@Valid @RequestBody CouponCalculateBO bo) {
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.calculateOrderAmount(bo)));
    }

    /**
     * 前端-返回用户使用最优惠的优惠劵ID
     *
     * @param bo 计算对象
     * @return 计算后的金额
     */
    @PostMapping("/id")
    public ResponseEntity selectCouponId(@Valid @RequestBody CouponIdBO bo) {
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.selectCouponId(bo)));
    }
}
