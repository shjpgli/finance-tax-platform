package com.abc12366.uc.web.order;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.order.Coupon;
import com.abc12366.uc.model.order.CouponActivity;
import com.abc12366.uc.model.order.CouponUser;
import com.abc12366.uc.model.order.bo.CouponActivityListBO;
import com.abc12366.uc.model.order.bo.CouponBO;
import com.abc12366.uc.model.order.bo.CouponListBO;
import com.abc12366.uc.model.order.bo.CouponUserListBO;
import com.abc12366.uc.service.order.CouponService;
import com.abc12366.uc.model.order.bo.CouponActivityBO;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
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
     * 查看优惠劵
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
     * 逻辑删除优惠劵
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
     * @param status 活动状态
     * @param pageNum 当前页
     * @param pageSize   每页大小
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

    @PostMapping("/activity")
    public ResponseEntity insertActivity(@Valid @RequestBody CouponActivityBO bo) {
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.insertActivity(bo)));
    }

    @PutMapping("/activity/{id}")
    public ResponseEntity updateActivity(@PathVariable String id, @Valid @RequestBody CouponActivityBO bo) {
        bo.setId(id);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", couponService.updateActivity(bo)));
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity selectOneActivity(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.selectOneActivity(id)));
    }

    @DeleteMapping("/activity/{id}")
    public ResponseEntity deleteActivity(@PathVariable String id) {
        LOGGER.info("{}", id);
        return ResponseEntity.ok(Utils.kv("data", couponService.deleteActivity(id)));
    }

    /**
     * 查询优惠劵活动用户优惠劵列表
     * @param status 优惠劵状态
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @return 优惠劵活动用户优惠劵列表
     */
    @GetMapping("/user")
    public ResponseEntity selectUserList(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {

        CouponUser bo = new CouponUser();
        if (StringUtils.isNotEmpty(status)) {
            bo.setStatus(status);
        }
        LOGGER.info("{},{},{}", bo, pageNum, pageSize);

        List<CouponUserListBO> dataList = couponService.selectUserList(bo, pageNum, pageSize);
        PageInfo<CouponUserListBO> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList, "total", pageInfo.getTotal()));
    }
}
