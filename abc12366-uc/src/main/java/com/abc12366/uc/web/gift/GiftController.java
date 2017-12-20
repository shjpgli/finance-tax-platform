package com.abc12366.uc.web.gift;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.gift.Gift;
import com.abc12366.uc.model.gift.UamountLog;
import com.abc12366.uc.model.gift.UgiftApply;
import com.abc12366.uc.model.gift.bo.*;
import com.abc12366.uc.model.order.bo.OrderBO;
import com.abc12366.uc.service.gift.GiftService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼物控制器
 *
 * @author lizhongwei
 * @create 2017-12-18 2:51 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/gift", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class GiftController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftController.class);

    @Autowired
    private GiftService giftService;

    /**
     * 后台-礼物列表查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @param name 礼物名称
     * @param status   状态
     * @return ResponseEntity {@linkplain Gift Gift}列表响应实体
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "status", required = false) String status) {
        Gift gift = new Gift();
        gift.setName(name);
        gift.setStatus(status);
        gift.setCategory(category);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Gift> dataList = giftService.selectList(gift);
        PageInfo<Gift> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return (dataList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 前台-礼物列表查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @param name 礼物名称
     * @param status   状态
     * @return ResponseEntity {@linkplain Gift Gift}列表响应实体
     */
    @GetMapping(path = "/show")
    public ResponseEntity selectUserGiftList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                            @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                            @RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "status", required = false) String status) {
        Gift gift = new Gift();
        gift.setName(name);
        gift.setStatus(status);
        gift.setCategory(category);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<Gift> dataList = giftService.selectList(gift);
        PageInfo<Gift> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return (dataList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 查看礼物详情
     * @param id 主键ID
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectById(@PathVariable("id") String id) {
        Gift gift = giftService.selectById(id);
        LOGGER.info("{}", gift);
        return ResponseEntity.ok(Utils.kv("data", gift));
    }

    /**
     * 礼物删除
     *
     * @param id 礼物ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        giftService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 礼物修改
     *
     * @param gift {@linkplain Gift GiftUpdateBO}
     * @param id           礼物ID
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody Gift gift, @PathVariable("id") String id) {
        LOGGER.info("{}", gift, id);
        gift.setId(id);
        GiftBO giftBO = giftService.update(gift);
        LOGGER.info("{}", giftBO);
        return ResponseEntity.ok(Utils.kv("data", giftBO));
    }

    /**
     * 礼物新增
     *
     * @param bo {@linkplain GiftBO GiftBO}
     * @return ResponseEntity GiftBO实体
     */
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody Gift bo) {
        LOGGER.info("{}", bo);
        Gift gift = giftService.insert(bo);
        LOGGER.info("{}", gift);
        return ResponseEntity.ok(Utils.kv("data", gift));
    }

    /**
     * 礼物批量删除
     *
     * @param ids {@linkplain Gift Gift}
     * @return ResponseEntity
     */
    /*@PostMapping(path = "/delete")
    public ResponseEntity batchDelete(@RequestParam(value = "ids", required = true) String ids) {
        LOGGER.info("{}", ids);
        giftService.batchDelete(ids);
        return ResponseEntity.ok(Utils.kv());
    }*/

    /**
     * 前台-用户的礼物申请列表
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @param userId 用户ID
     * @return ResponseEntity {@linkplain Gift Gift}列表响应实体
     */
    @GetMapping(path = "/apply/user/list")
    public ResponseEntity selectUserGiftListById(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                 @RequestParam(value = "userId", required = true) String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<UgiftApplyBO> dataList = giftService.selectUgiftApplyList(map);
        PageInfo<UgiftApplyBO> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return (dataList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 后台-礼物申请列表
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @param name 收件人名称
     * @param giftName 礼品名称
     * @param status 状态
     * @return ResponseEntity {@linkplain Gift Gift}列表响应实体
     */
    @GetMapping(path = "/apply/list")
    public ResponseEntity selectApplyList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                                 @RequestParam(value = "status", required = false) String status,
                                                 @RequestParam(value = "giftName", required = false) String giftName,
                                                 @RequestParam(value = "name", required = false) String name) {
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("name",name);
        map.put("giftName",giftName);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<UgiftApplyBO> dataList = giftService.selectUgiftApplyList(map);
        PageInfo<UgiftApplyBO> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return (dataList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 用户兑换礼物申请
     *
     * @param giftId 礼物名称
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @PostMapping(path = "/apply/user/{userId}/{giftId}")
    public ResponseEntity buyGift(@Valid @RequestBody UgiftApply ugiftApply,
                                  @PathVariable("giftId") String giftId,
                                  @PathVariable("userId") String userId) {
        LOGGER.info("{},{}", giftId,userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("giftId",giftId);
        map.put("ugiftApply",ugiftApply);
        giftService.buyGift(map);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 管理员审核礼物申请
     *
     * @param giftCheckBO 礼物审核BO 礼物发货BO
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @PutMapping(path = "/apply/check")
    public ResponseEntity checkGiftBuy(@Valid @RequestBody GiftCheckBO giftCheckBO) {
        LOGGER.info("{}", giftCheckBO);
        giftService.checkGiftBuy(giftCheckBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 管理员发货
     *
     * @param giftSendBO 礼物审核BO
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @PutMapping(path = "/apply/send")
    public ResponseEntity sendGift(@Valid @RequestBody GiftSendBO giftSendBO) {
        LOGGER.info("{}", giftSendBO);
        giftService.sendGift(giftSendBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 根据ID查找用户已领取礼物详情
     *
     * @param giftId 礼物名称
     * @return ResponseEntity {@linkplain Gift Gift}响应实体
     */
    @GetMapping(path = "/apply/user/{userId}/{giftId}")
    public ResponseEntity selectGiftByGiftId(@PathVariable("giftId") String giftId,
                                             @PathVariable("userId") String userId) {
        LOGGER.info("{},{}", giftId,userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("giftId",giftId);
        Gift data = giftService.selectGiftByGiftId(map);
        LOGGER.info("{}", data);
        return ResponseEntity.ok(Utils.kv("data", data));
    }

    /**
     * 后台-根据用户ID查找礼包金额记录
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @param userId 用户ID
     * @return ResponseEntity {@linkplain Gift Gift}列表响应实体
     */
    @GetMapping(path = "/uamount/list")
    public ResponseEntity selectUamountLogList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                          @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                          @RequestParam(value = "userId", required = false) String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<UamountLog> dataList = giftService.selectUamountLogList(map);
        PageInfo<UamountLog> pageInfo = new PageInfo<>(dataList);
        LOGGER.info("{}", dataList);
        return (dataList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }
}
